package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Eventos.EventoCerrarSesion;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;

/**
 * Created by julian on 26/06/16.
 */
public class CerrarSesionTask extends AsyncTask<String,Void,String> {

    private ActivityFlujo activity;
    private EventoCerrarSesion evento = new EventoCerrarSesion();

    public CerrarSesionTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {

        if(Network.isOnline(activity)) {

            ResponseHelper response  = FitnessTimeApplication.getLogginServicio().cerrar(FitnessTimeApplication.getSession());

            if(response.getCodigo()!=200)
            {
                evento.setCode(response.getCodigo());
                evento.setMensaje("No se pudo cerrar sesión");
            }
        }
        else
        {
            evento.setCode(500);
            evento.setMensaje("No se pudo cerrar sesión por falta de conexión");
        }
        return "";
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
