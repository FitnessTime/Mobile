package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoSincronizarRutinas;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.google.gson.Gson;

/**
 * Created by julian on 22/05/16.
 */
public class EliminarRutinaTask extends AsyncTask<Rutina,Void,String> {

    private ActivityFlujo activity;

    public EliminarRutinaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Rutina... rutinas) {
        String mensaje = "Rutina eliminada con exito.";

        if(Network.isOnline(activity))
        {
            Gson gson = new Gson();
            ResponseHelper response = new ServicioRutina().eliminarAPI(rutinas[0].getId());
            if(response.getCodigo()==200)
            {
                new ServicioRutina().actualizar(gson.fromJson(response.getMensaje(), RutinaDTO.class));
            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        FitnessTimeApplication.getEventBus().post(new EventoSincronizarRutinas());
    }
}
