package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizar;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizarRegistro;
import com.fitnesstime.fitnesstime.Eventos.EventoRegistro;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by julian on 25/06/16.
 */
public class RegistroEditarTask extends AsyncTask<Registro,Void,String> {

    private ActivityFlujo activity;
    private EventoActualizarRegistro evento = new EventoActualizarRegistro();

    public RegistroEditarTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Registro... registros) {

        if(Network.isOnline(activity)) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            ResponseHelper response = FitnessTimeApplication.getRegistroServicio().actualizar(registros[0]);
            if(response.getCodigo() == 200)
            {
                new ServicioSecurityToken().actualizar(gson.fromJson(response.getMensaje(), SecurityToken.class));
                evento.setMensaje("Usuario modificado éxitosamente.");
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