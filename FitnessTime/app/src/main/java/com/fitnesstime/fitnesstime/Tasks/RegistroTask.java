package com.fitnesstime.fitnesstime.Tasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityLoggin;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Eventos.EventoLoggin;
import com.fitnesstime.fitnesstime.Eventos.EventoRegistro;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.google.gson.Gson;

/**
 * Created by julian on 12/05/16.
 */
public class RegistroTask extends AsyncTask<Registro,Void,String> {

    private ActivityFlujo activity;
    private EventoRegistro evento = new EventoRegistro();

    public RegistroTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Registro... registros) {

        if(Network.isOnline(activity)) {
            ResponseHelper response = FitnessTimeApplication.getRegistroServicio().registrar(registros[0]);
            if(response.getCodigo() == 200)
            {
                evento.setMensaje("Usuario registrado éxitosamente.");
            }
            else {
                evento.setError(response.getMensaje());
            }
        }
        else
        {
            evento.setError("No tiene conexión a internet.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String mensaje) {
        super.onPostExecute(mensaje);
         FitnessTimeApplication.getEventBus().post(evento);
    }
}
