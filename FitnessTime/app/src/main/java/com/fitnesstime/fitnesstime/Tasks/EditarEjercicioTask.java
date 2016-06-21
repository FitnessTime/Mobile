package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.EjercicioAssembler;
import com.fitnesstime.fitnesstime.DTOs.EjercicioDTO;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoActuaizarEjercicio;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by julian on 02/06/16.
 */
public class EditarEjercicioTask extends AsyncTask<Ejercicio,Void,String> {

    private ActivityFlujo activity;
    private EventoActuaizarEjercicio evento = new EventoActuaizarEjercicio();
    public EditarEjercicioTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Ejercicio... ejercicios) {

        if(Network.isOnline(activity))
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String param = gson.toJson(EjercicioAssembler.toDTO(ejercicios[0]), EjercicioDTO.class);
            ResponseHelper response = new ServicioEjercicio().editarAPI(param);
            if(response.getCodigo()==200)
            {
                Ejercicio ejercicio = EjercicioAssembler.fromDTO(gson.fromJson(response.getMensaje(), EjercicioDTO.class));
                new ServicioEjercicio().actualizar(ejercicio);
                new ServicioRutina().marcarComoSincronizada(ejercicio.getRutinaId());
            }
            else
            {
                evento.setMensaje("Ejercicio modificado, pero no sincronizado.");
            }
        }
        else
        {
            evento.setMensaje("Ejercicio modificado, pero no sincronizado por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
