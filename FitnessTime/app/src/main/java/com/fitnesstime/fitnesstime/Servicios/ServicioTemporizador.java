package com.fitnesstime.fitnesstime.Servicios;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.Threads.ThreadTemporizador;
import com.fitnesstime.fitnesstime.Util.HelperNotificacion;

public class ServicioTemporizador extends Service {

    private ThreadTemporizador mThread;
    Bundle myB = new Bundle();                 //used for creating the msgs
    public Handler mHandler = new Handler(){   //handles the INcoming msgs
        @Override public void handleMessage(Message msg)
        {
            myB = msg.getData();
            String s = myB.getString(Constantes.CODIGO_HANDLER_SERVICIO_TEMPORIZADOR);
            FitnessTimeApplication.getEventBus().post(new EventoTemporizador(s));
        }
    };

    public ServicioTemporizador(){}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HelperNotificacion.crearNotificacionEnServicio(this, startId);
        int intervalo1 = intent.getIntExtra("intervalo1", 0);
        int intervalo2 = intent.getIntExtra("intervalo2", 0);
        mThread = new ThreadTemporizador(mHandler, intervalo1, intervalo2);
        mThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.stopThread();
    }

}
