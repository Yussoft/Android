package com.blogspot.approdriguez.puntomovimientosonido;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    MediaPlayer player;
    MediaPlayer player2;
    public boolean posA=false;
    public boolean posB=false;
    private int knockTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player = MediaPlayer.create(this, R.raw.song);
        player2 = MediaPlayer.create(this, R.raw.comming);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener( this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        setContentView(R.layout.activity_main);

    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;


        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //player.stop();
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //if((x<=9.99 && x>=8.60) && (y<=0.15 && y>=0.05) && (z<= 1.99 && z>=0.80)) {
            if(z<3){
                posA = true;
            }

            //if((x<=0.30 && x>= 0.10) && (y<=0.35 && y>=0.10) && (z<= 9.20 && z>=8.00)) {
            if(z>8){
                posB = true;
            }

        }

        if(posA && posB)
        {
            posA = false;
            posB = false;

            player.start();
            knockTimes=knockTimes+1;

        }

        if(knockTimes==7) {
            player2.start();
            knockTimes=0;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
