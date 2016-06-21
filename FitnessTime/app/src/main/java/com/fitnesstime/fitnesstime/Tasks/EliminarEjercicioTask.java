package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DTOs.EjercicioDTO;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoEliminarEjercicio;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by julian on 20/06/16.
 */
public class EliminarEjercicioTask extends AsyncTask<Ejercicio,Void,String> {

    private ActivityFlujo activity;
    private EventoEliminarEjercicio evento = new EventoEliminarEjercicio();
    public EliminarEjercicioTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Ejercicio... ejercicios) {

        if(Network.isOnline(activity))
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            if(ejercicios[0].getIdWeb()!=null)
            {
                ResponseHelper response = new ServicioEjercicio().eliminarAPI(ejercicios[0].getIdWeb(), ejercicios[0].getEsDeCarga());
                if(response.getCodigo()==200)
                {
                    new ServicioEjercicio().actualizar(gson.fromJson(response.getMensaje(), EjercicioDTO.class));
                }
                else
                {
                    evento.setMensaje("Rutina eliminada, pero no sincronizada.");
                }
            }
        }
        else
        {
            evento.setMensaje("Ejercicio eliminado, pero no sincronizado por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        FitnessTimeApplication.getEventBus().post(evento);
    }
}
