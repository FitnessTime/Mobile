package com.fitnesstime.fitnesstime.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityTimmer;
import com.fitnesstime.fitnesstime.R;

public class ServicioTemporizador extends Service {

    private Intent intentPadre;
    private int id;
    private IBinder mBinder = new LocalBinder();

    public ServicioTemporizador() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        intentPadre = intent;
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = startId;
        cargarNotificacion();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public String getName(String s) {
        return s;
    }

    public class LocalBinder extends Binder {

        public ServicioTemporizador getService()
        {
            return ServicioTemporizador.this;
        }
    }
}
