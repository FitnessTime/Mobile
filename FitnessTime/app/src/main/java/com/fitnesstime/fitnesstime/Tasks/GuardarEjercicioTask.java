package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.EjercicioAssembler;
import com.fitnesstime.fitnesstime.DTOs.EjercicioDTO;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarEjercicio;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.google.gson.Gson;

/**
 * Created by julian on 12/06/16.
 */
public class GuardarEjercicioTask extends AsyncTask<Ejercicio,Void,String> {

    private ActivityFlujo activity;

    public GuardarEjercicioTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Ejercicio... ejercicios) {

        if(Network.isOnline(activity))
        {
            Gson gson = new Gson();
            String param = gson.toJson(EjercicioAssembler.toDTO(ejercicios[0]), EjercicioDTO.class);
            ResponseHelper response = new ServicioEjercicio().guardarAPI(param);
            if(response.getCodigo()==200)
            {
                new ServicioEjercicio().actualizar(EjercicioAssembler.fromDTO(gson.fromJson(response.getMensaje(), EjercicioDTO.class)));
            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(new EventoGuardarEjercicio());
    }
}
