package com.fitnesstime.fitnesstime.Application;

/**
 * Created by julian on 04/09/16.
 */

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Jobs.SincronizarSchedulerService;

public class BootReceiver extends BroadcastReceiver {


    // LLamo a los servicios que quiero levantar
    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent service = new Intent(context, PushService.class);
        //context.startService(service);


    }

}
