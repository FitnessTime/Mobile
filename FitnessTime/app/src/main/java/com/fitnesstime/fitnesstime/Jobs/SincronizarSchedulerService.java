package com.fitnesstime.fitnesstime.Jobs;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.fitnesstime.fitnesstime.Tasks.SincronizacionRutinasSchedulerTask;
import com.fitnesstime.fitnesstime.Tasks.SincronizacionRutinasTask;

public class SincronizarSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean conectado = netInfo != null && netInfo.isConnectedOrConnecting();
        boolean estaAutenticado = new ServicioSecurityToken().estaAutenticado();
        if(conectado && estaAutenticado)
            new SincronizacionRutinasSchedulerTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }

}
