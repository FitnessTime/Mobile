package com.fitnesstime.fitnesstime.Threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;

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
    private Handler inHandler = new Handler(){  //handles the INcoming msgs
        @Override public void handleMessage(Message msg)
        {
            myB = msg.getData();
        }
    };

    public ThreadTemporizador(Handler mHandler) {
        outHandler = mHandler;
    }

    public void run(){
        startTime = SystemClock.uptimeMillis();
        while(continua) {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            sendMsgToMainThread(String.valueOf(mins) + ":" + String.valueOf(secs) + ":" + String.valueOf(milliseconds));
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