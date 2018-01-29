package com.sensorplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
	private TextView mTime;
	private TextView mAccuracy;
	DataBaseHelper myDB;
	Button btnAddData;
	ToggleButton btnToggle;
	boolean Regi;
	Thread mthread;

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
		mTime = (TextView)findViewById(R.id.time);
		mAccuracy = (TextView)findViewById(R.id.accuracy);
        btnAddData = (Button) findViewById(R.id.measurerecords);
		myDB = new DataBaseHelper(this);
        this.AddData();

		btnToggle = (ToggleButton) this.findViewById(R.id.measuretoggle);
		btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (isChecked == true) {
					Regi = true;
					SensorValuesActivity(mSensorManager);
				} else {
					Regi = false;
					SensorValuesActivity(mSensorManager);
				}
			}
		});
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
		mTime.setText("Time: " + String.valueOf(event.timestamp));
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	Button.OnClickListener btnRec = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(SensorValuesActivity.this, SensorRecDisplay.class);
			startActivity(intent);
		}
	};

	public void SensorValuesActivity(SensorManager mSensorManager) {
		if (Regi) {
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ALL);
			mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			mSensorManager.unregisterListener(this);
		}
	}

	public void AddData() {
	    btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mthread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (true) {
                            try {
                                Thread.sleep(1000);
                                boolean inInserted = myDB.insertData(mEventValue_0.getText().toString(), mEventValue_1.getText().toString(), mEventValue_2.getText().toString(), mTime.getText().toString());
                                /*if (inInserted = true)
                                    Toast.makeText(SensorValuesActivity.this, "Recording Data!", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(SensorValuesActivity.this, "No Data..", Toast.LENGTH_SHORT).show();*/
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                mthread.start();
            }
        });

    }
}
