package com.sensorplay;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Jun on 2018-01-21.
 */

public interface Memo {}

/*  //지자기 센서가 있는지 체크
    private SensorManager mSensorManager;
    ...
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {

            } else {

            }*/


/*  private SensorManager mSensorManager;
    private Sensor mSensor;
    ...
    mSensorManager = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
    if (mSensorManager.getDefaultSensor (Sensor.TYPE_GRAVITY) != null) {
        List<Sensor> gravSensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        for (int i=0, i<graveSensor.size(); i++) {
            if ((gravSensors.get(i).getVendor().contains("Google Inc.")) &&
        (gravSensors.get(i).getVendor() ==3)) {
                //Ver.3의 중력 센서를 사용함
                mSensor = gravSensors.get(i)
                }
            }
        } else {
        //가속도 센서를 사용함
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mSensor = mSensorManager.getDefaultSensor (Sensor.TYPE_ACCELEROMETER);
        } else {
            //죄송합니다. 이 기기에는 가속도 센서가 없습니다.
        }
        }*/


/*    //기준좌표에 대한 현재 방위를 결정함, 폰을 4번에 걸쳐 360도 회전 했을 경우
    String windoSrvc = Context.WINDOW_SERVICE;
    WindowManager wm = ((WindowManager) getSystemService (windoSrvc));
    Display display = wm.getDefaultDisplay();
    int rotation = display.getRotation();

    int x_axis = SensorManager.AXIS_X;
    int y_axis = SensorManager.AXIS_Y;

    switch (rotation) {
        case (Surface.ROTATION_0): break;
        case (Surface.ROTATION_90):
            x_axis = SensorManager.AXIS_Y;
            y_axis = SensorManager.AXIS_MINUS_X;
            break;
        case (Surface.ROTATION_180):
            y_axis = SensorManager.AXIS_MINUS_Y;
            break;
        case (Surface.ROTATION_270):
            x_axis = SensorManager.AXIS_MINUS_Y;
            y_axis = SensorManager.AXIS_X;
            break;
        default: break;
            }
    SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
    //리맵핑된 새로운 방향값을 결정
    SensorManager.getOrientation(outR, values);*/


/*  //portrait mode, landscape mode
    if |FAz| > 0.5[G] && |FAy| > 0.5[G] && |FAx| < 0.4 < [G], Buttom mode
    else if |FAz| > 0.5[G] && |FAy| < -0.5[G] && |FAx| < 0.4 < [G], Top mode
    else if |FAz| > 0.5[G] && |FAx| > 0.5[G] && |FAy| < 0.4 < [G], Left mode
    else if |FAz| > 0.5[G] && |FAx| < - 0.5[G] && |FAy| < 0.4 < [G], Right mode
    수직축: y, x축: x, y축: z*/

