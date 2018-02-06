package com.sensorplay;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import static android.os.SystemClock.sleep;


public class SensorValuesActivity extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
	private int mSensorType;
	private Sensor mSensor;
	private TextView mEventValue_0;
	private TextView mEventValue_1;
	private TextView mEventValue_2;
	private TextView mEventValue_3;
	private TextView mEventValue_4;
	private TextView mEventValue_5;
	private TextView mEventValue_6;
	private TextView mAccuracy;
    private TextView mTime;
    private TextView mToday;
    private TextView mSensorName;
    Chronometer mCurrentTime;
	DataBaseHelper myDB;
	ToggleButton toggleStartStop, btnAddData;
	Button btnDeleteData, btnSendData;
	boolean Regi;
	long mNow;
	long mSampling = 0;
	Date mDate;
    ValueThread mThread = new ValueThread();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.values_layout);

		Intent intent = getIntent();
		mSensorType = intent.getIntExtra(getResources().getResourceName(R.string.sensor_type), 0);
		mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(mSensorType);
		mEventValue_0 = (TextView)findViewById(R.id.event0);
		mEventValue_1 = (TextView)findViewById(R.id.event1);
		mEventValue_2 = (TextView)findViewById(R.id.event2);
		mEventValue_3 = (TextView)findViewById(R.id.event3);
		mEventValue_4 = (TextView)findViewById(R.id.event4);
		mEventValue_5 = (TextView)findViewById(R.id.event5);
		mEventValue_6 = (TextView)findViewById(R.id.event6);
        mAccuracy = (TextView)findViewById(R.id.accuracy);
		mTime = (TextView)findViewById(R.id.time);
		mCurrentTime = (Chronometer) findViewById(R.id.currenttime);
        mCurrentTime.setText("00:00:00");
        mToday = (TextView)findViewById(R.id.date);
        mToday.setText(getTime());
        mSensorName = (TextView)findViewById(R.id.sensornamedisplay);
        mSensorName.setText(mSensor.getName());
        toggleStartStop = (ToggleButton) this.findViewById(R.id.toggle_start);
        btnAddData = (ToggleButton) findViewById(R.id.toggle_rec);
        btnDeleteData = (Button) findViewById(R.id.btn_clear);
        btnSendData = (Button) findViewById(R.id.btn_mail);
		myDB = new DataBaseHelper(this);
        AddData();
        StartStop();
        CurrentTime();
        DeleteData();
        MailData();
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

    @Override
    public void onBackPressed() {
        Regi = false;
        SensorValuesActivity(mSensorManager);
        finish();   //현재 화면 종료 (이전 화면으로 전환)
    }

	@Override
	public void onSensorChanged(final SensorEvent event) {

		mAccuracy.setText(String.valueOf(event.accuracy));
		mTime.setText(String.valueOf(event.timestamp));
		mEventValue_0.setText(String.valueOf(event.values[0]));
		if(event.values.length>1) {
			mEventValue_1.setText(String.valueOf(event.values[1]));
		}
		if(event.values.length>2) {
			mEventValue_2.setText(String.valueOf(event.values[2]));
		}
		if(event.values.length>3) {
			mEventValue_3.setText(String.valueOf(event.values[3]));
		}
		if(event.values.length>4) {
			mEventValue_4.setText(String.valueOf(event.values[4]));
		}
		if(event.values.length>5) {
			mEventValue_5.setText(String.valueOf(event.values[5]));
		}
		if(event.values.length>6) {
			mEventValue_6.setText(String.valueOf(event.values[6]));
		}
	}

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void CurrentTime() {
        mCurrentTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int) (time/3600000);
                int m = (int) ((time-h*3600000)/60000);
                int s = (int) ((time-h*3600000-m*60000)/1000);
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                chronometer.setText(hh+":"+mm+":"+ss);
            }
        });

    }

    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String getTime() {
	    mNow = System.currentTimeMillis();
	    mDate = new Date(mNow);
	    return mFormat.format(mDate);
    }

	public void SensorValuesActivity(SensorManager mSensorManager) {
		if (Regi) {
            Intent intent = getIntent();
            mSensorType = intent.getIntExtra(getResources().getResourceName(R.string.sensor_type), 0);
            mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(mSensorType);
			mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			mSensorManager.unregisterListener(this);
		}
	}

	public void StartStop() {
        toggleStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true) {
                    Regi = true;
                    Toast.makeText(SensorValuesActivity.this, "START!", Toast.LENGTH_SHORT).show();
                    SensorValuesActivity(mSensorManager);
                    mCurrentTime.setBase(SystemClock.elapsedRealtime());
                    mCurrentTime.start();
                } else {
                    Regi = false;
                    Toast.makeText(SensorValuesActivity.this, "PAUSE!", Toast.LENGTH_SHORT).show();
                    SensorValuesActivity(mSensorManager);
                    mCurrentTime.stop();
                }

            }
        });
    }


    public class ValueThread extends Thread {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(mSampling);
                    myDB.insertData(mEventValue_0.getText().toString(), mEventValue_1.getText().toString(), mEventValue_2.getText().toString(), mTime.getText().toString());
                    Log.d("aabc", "x: "+mEventValue_0.getText().toString() +" y: "+ mEventValue_1.getText().toString() +" z: "+ mEventValue_2.getText().toString() +" TIME: "+ mTime.getText().toString());
                } catch (InterruptedException e) {
                    Toast.makeText(SensorValuesActivity.this, "THREAD ERROR!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

	public void AddData() {
	    btnAddData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {

                if (isChecked == true) {
                    boolean inInserted = myDB.insertData("Start",mToday.getText().toString(),mSensorName.getText().toString(),null);
                    if (inInserted == true) {
                        Toast.makeText(SensorValuesActivity.this, "Recording Data!", Toast.LENGTH_SHORT).show();
                        myDB.dbopen();
                        mThread.start();
                    } else {
                        Toast.makeText(SensorValuesActivity.this, "ERROR REC", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    myDB.insertData("Finish",mToday.getText().toString(),mSensorName.getText().toString(),null);
                    Toast.makeText(SensorValuesActivity.this, "Finish Recording", Toast.LENGTH_SHORT).show();
                    mThread.interrupt();
                    myDB.dbclose();
                }
            }
        });

    }

    public void DeleteData() {
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.deleteDbTable();
                Toast.makeText(SensorValuesActivity.this, "DB deleting is done!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void backupDatabase(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

//            String packageName = context.getApplicationInfo().packageName;

            if (sd.canWrite()) {
                String currentDBPath = String.format("//data//com.sensorplay//databases//%s",
                        databaseName);
                String backupDBPath = String.format("%s", databaseName);
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void MailData() {
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backupDatabase("sensorValue.db");

//                File data = Environment.getDataDirectory();
                String dbName = "sensorValue.db";
                String currentDBPath = "//storage//emulated//0//" + dbName;
                File exportFile = new File(currentDBPath);
                Uri uri = Uri.fromFile(exportFile);

                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("file/*");
                String[] adress = {"icevolt123@naver.com"};
                email.putExtra(Intent.EXTRA_EMAIL, adress);
                email.putExtra(Intent.EXTRA_SUBJECT, "DB file from Jun");
                email.putExtra(Intent.EXTRA_TEXT, "Sensor DB file attached");
                email.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(email);
            }
        });
    }
}
