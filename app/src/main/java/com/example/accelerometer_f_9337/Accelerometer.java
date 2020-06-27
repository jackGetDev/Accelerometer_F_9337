package com.example.accelerometer_f_9337;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    private long lastUpdate=0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD=600;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        hello=(TextView) findViewById(R.id.hello);
    }


    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor=sensorEvent.sensor;

        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            float x=sensorEvent.values[0];
            float y= sensorEvent.values[1];
            float z= sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if((curTime-lastUpdate)>100){
                long diffTime = (curTime-lastUpdate);
                lastUpdate=curTime;

                float speed = Math.abs(x+y+z-last_x-last_y-last_z)/diffTime*10000;

                if(speed>SHAKE_THRESHOLD)
                {
                    Toast.makeText(getApplicationContext(),"SHAKE",Toast.LENGTH_SHORT).show();
                    Log.d("key","asd");
                }
                last_x=x;
                last_y=y;
                last_z=z;
            }
        }

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){}
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


}
