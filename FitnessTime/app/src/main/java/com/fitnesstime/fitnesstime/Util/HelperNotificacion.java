package com.fitnesstime.fitnesstime.Util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.fitnesstime.fitnesstime.Activities.ActivityTemporizador;
import com.fitnesstime.fitnesstime.R;

/**
 * Created by julian on 03/03/16.
 */
public final class HelperNotificacion {

    public static void crearNotificacionEnServicio(Service servicio, int id)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(servicio);
        mBuilder.setSmallIcon(R.mipmap.icono);
        mBuilder.setContentTitle("Fitness Time");
        mBuilder.setContentText("Temporizador aerobico en proceso...");
        Intent resultIntent = new Intent(servicio, ActivityTemporizador.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(servicio);
        stackBuilder.addParentStack(ActivityTemporizador.class);
        mBuilder.setAutoCancel(true);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) servicio.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(id, mBuilder.build());
    }
}
