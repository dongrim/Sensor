package com.sensorplay;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jun on 2018-01-19.
 */

public class SensorRec extends Observable implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long mEventValue_0;
    private float mEventValue_1;
    private float mEventValue_2;
    private TextView mEventValue_3;
    private TextView mEventValue_4;
    private TextView mEventValue_5;
    private TextView mEventValue_6;
    private long mTime;
    private TextView mAccuracy;

    public SensorRec(SensorManager sensorManager, Observer o) {
        this(sensorManager);
        addObserver(o);
    }

    public SensorRec(SensorManager mSensorManager) {
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public SensorRec(long mTime, long mEventValue_0, float mEventValue_1, float mEventValue_2) {
        this.mTime = mTime;
        this.mEventValue_0 = mEventValue_0;
        this.mEventValue_1 = mEventValue_1;
        this.mEventValue_2 = mEventValue_2;
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {

        long mTime = event.timestamp;
        long mEventValue_0 = (long) event.values[0];
        float mEventValue_1 = event.values[1];
        float mEventValue_2 = event.values[2];

        SensorRec data = new SensorRec(mTime, mEventValue_0, mEventValue_1, mEventValue_2);
        this.setChanged();
        this.notifyObservers(data);
    }

    public long getTimestamp() {
        return mTime;
    }

    public long getValueX() {
        return mEventValue_0;
    }

    public float getValueY() {
        return mEventValue_1;
    }

    public float getValueZ() {
        return mEventValue_2;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int Accuracy) {
    }


}
