package com.fitnesstime.fitnesstime.Threads;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Util.HelperTono;

/**
 * Created by julian on 03/03/16.
 */
public class ThreadTemporizador extends Thread{

    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private int secs = 0;
    private int mins = 0;
    private Handler outHandler;
    Bundle myB = new Bundle();
    private boolean continua = true;
    private int intervalo1;
    private int intervalo2;
    private Handler inHandler = new Handler(){  //handles the INcoming msgs
        @Override public void handleMessage(Message msg)
        {
            myB = msg.getData();
        }
    };

    public ThreadTemporizador(Handler mHandler, int int1, int int2) {
        outHandler = mHandler;
        this.intervalo1 = int1;
        this.intervalo2 = int2;
    }

    public void run(){
        startTime = SystemClock.uptimeMillis();
        int int1 = intervalo1;
        int int2 = intervalo1 + intervalo2;
        while(continua) {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            if(mins==60)
                intervalo1 = intervalo1 / 60;
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            sendMsgToMainThread(String.valueOf(mins) + ":" + String.valueOf(secs) + ":" + String.valueOf(milliseconds));
            if(mins==int1 && intervalo1!=0)
            {
                HelperTono.generarTono(1000);
                int1 = int1 + intervalo1 + intervalo2;

            }
            if(mins==int2 && intervalo2!=0)
            {
                HelperTono.generarTono(2000);
                int2 = int2 + intervalo2 + intervalo1;

            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread()
    {
        continua = false;
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Handler getHandler(){
        return inHandler;
    }

    private void sendMsgToMainThread(String tiempo){
        Message msg = outHandler.obtainMessage();
        myB.putString(Constantes.CODIGO_HANDLER_SERVICIO_TEMPORIZADOR, tiempo);
        msg.setData(myB);
        outHandler.sendMessage(msg);
    }
}