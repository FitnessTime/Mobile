package com.fitnesstime.fitnesstime.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityTimmer;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Eventos.EventoComenzarTemporizador;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.R;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

public class ServicioTemporizador extends Service {

    private Intent intentPadre;
    private int id;
    private MyThread mThread;
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private int secs = 0;
    private int mins = 0;
    Bundle myB = new Bundle();                 //used for creating the msgs
    public          Handler mHandler = new Handler(){   //handles the INcoming msgs
        @Override public void handleMessage(Message msg)
        {
            myB = msg.getData();
            String s = myB.getString("THREAD DELIVERY");
            FitnessTimeApplication.getEventBus().post(new EventoTemporizador(s));
        }
    };

    public ServicioTemporizador() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        intentPadre = intent;
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = startId;
        cargarNotificacion();
        startTime = SystemClock.uptimeMillis();
        mThread = new MyThread(mHandler);
        mThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThread.stopThread();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    private void cargarNotificacion()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.icono);
        mBuilder.setContentTitle("Fitness Time");
        mBuilder.setContentText("Temporizador aerobico en proceso...");
        Intent resultIntent = new Intent(this, ActivityTimmer.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ActivityTimmer.class);
        mBuilder.setAutoCancel(true);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());
    }

    public class MyThread extends Thread{
        //Properties:
        private final   String TAG = "MyThread";            //Log tag
        private         Handler outHandler;                 //handles the OUTgoing msgs
        Bundle          myB = new Bundle();                 //used for creating the msgs
        private boolean continua = true;
        private         Handler inHandler = new Handler(){  //handles the INcoming msgs
            @Override public void handleMessage(Message msg)
            {
                myB = msg.getData();
            }
        };

        //Methods:
        //--------------------------
        public void run(){
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
        //--------------------------
        public MyThread(Handler mHandler) {
            //C-tor that get a reference object to the MainActivity handler.
            //this is how we know to whom we need to connect with.
            outHandler = mHandler;
        }
        //--------------------------
        public Handler getHandler(){
            //a Get method which return the handler which This Thread is connected with.
            return inHandler;
        }
        //--------------------------
        private void sendMsgToMainThread(String tiempo){
            Message msg = outHandler.obtainMessage();
            myB.putString("THREAD DELIVERY", tiempo);
            msg.setData(myB);
            outHandler.sendMessage(msg);
        }
    }
}
