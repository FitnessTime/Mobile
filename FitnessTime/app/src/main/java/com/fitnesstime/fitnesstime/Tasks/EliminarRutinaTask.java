package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoEliminarRutina;
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
    private EventoEliminarRutina evento = new EventoEliminarRutina();
    public EliminarRutinaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Rutina... rutinas) {

        if(Network.isOnline(activity))
        {
            Gson gson = new Gson();
            if(rutinas[0].getIdWeb()!=null)
            {
                ResponseHelper response = new ServicioRutina().eliminarAPI(rutinas[0].getIdWeb());
                if(response.getCodigo()==200)
                {
                    new ServicioRutina().actualizar(gson.fromJson(response.getMensaje(), RutinaDTO.class));
                }
                else
                {
                    evento.setMensaje("Rutina eliminada, pero no sincronizada.");
                }
            }
        }
        else
        {
            evento.setMensaje("Rutina eliminada, pero no sincronizado por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        FitnessTimeApplication.getEventBus().post(evento);
    }
}
