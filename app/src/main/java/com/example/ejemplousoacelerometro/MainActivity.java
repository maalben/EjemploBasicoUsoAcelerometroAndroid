package com.example.ejemplousoacelerometro;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    int contador = 0;
    private ImageView cleotilde;
    private ImageView donramon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cleotilde = findViewById(R.id.imgcleotilde);
        donramon = findViewById(R.id.imgdonramon);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensor == null){
            finish();
        }

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float inclinacion = sensorEvent.values[0];
                if(inclinacion<-5 && contador==0){
                    contador++;
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(102, 239, 88));
                    cleotilde.setVisibility(View.INVISIBLE);
                    donramon.setVisibility(View.VISIBLE);
                }else if(inclinacion > 5 && contador==1){
                    contador++;
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(239, 88, 113));
                    cleotilde.setVisibility(View.VISIBLE);
                    donramon.setVisibility(View.INVISIBLE);
                }else if(inclinacion==0){
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(0, 0, 0));
                    cleotilde.setVisibility(View.VISIBLE);
                    donramon.setVisibility(View.VISIBLE);
                }

                if(contador == 2){
                    contador = 0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                //No se requiere utilizar
            }
        };
        start();
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener, sensor,SENSOR_DELAY_NORMAL);
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }
}
