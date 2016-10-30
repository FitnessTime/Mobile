package com.fitnesstime.fitnesstime.Jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.fitnesstime.fitnesstime.Tasks.GuardarPasosTask;
import com.fitnesstime.fitnesstime.Tasks.SincronizacionRutinasSchedulerTask;

/**
 * Created by julian on 19/10/16.
 */
public class GuardarPasosJob extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean conectado = netInfo != null && netInfo.isConnectedOrConnecting();
        boolean estaAutenticado = new ServicioSecurityToken().estaAutenticado();
        if(conectado && estaAutenticado)
            new GuardarPasosTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }

}