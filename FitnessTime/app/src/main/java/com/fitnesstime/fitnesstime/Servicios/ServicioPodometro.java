package com.fitnesstime.fitnesstime.Servicios;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.Paso;
import com.fitnesstime.fitnesstime.Eventos.EventoPodometro;
import com.fitnesstime.fitnesstime.Util.HelperNotificacion;

import java.util.Date;

/**
 * Created by julian on 25/09/16.
 */
public class ServicioPodometro extends Service {

    private static final String TAG = "SERVICIO_PODOMETRO";
    private float previousY;
    private float currentY;
    private int numSteps;
    private SensorManager sensorManager;
    private float acceleration;
    private ServicioPaso servicioPasos = new ServicioPaso();
    private int threshold;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private SensorEventListener sensorEventListener;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {

        Paso pasos = servicioPasos.getByFecha(new Date());
        threshold = 10;

        previousY = 0;
        currentY = 0;
        numSteps = pasos != null ? pasos.getPasosDados() : 0;
        acceleration = 0.00f;
        enableAccelerometerListening();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        servicioPasos.guardarPasos(numSteps);
        servicioPasos.guardarAPI(servicioPasos.getByFecha(new Date()));
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        servicioPasos.guardarPasos(numSteps);
        servicioPasos.guardarAPI(servicioPasos.getByFecha(new Date()));
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void enableAccelerometerListening()
    {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                currentY = y;

                if(Math.abs(currentY-previousY) > threshold)
                {
                    numSteps++;
                    new ServicioPaso().guardarPasos(numSteps);
                    if(FitnessTimeApplication.getPasosMinimosUsuario() == numSteps)
                        HelperNotificacion.crearNotificacionObjetivoCumplido(ServicioPodometro.this, 123165);
                    FitnessTimeApplication.getEventBus().post(new EventoPodometro(numSteps));
                }

                previousY = y;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager = (SensorManager)getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }
}
