package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizarRutina;
import com.fitnesstime.fitnesstime.Eventos.EventoSincronizarRutinas;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julian on 01/05/16.
 */
public class SincronizacionRutinasTask extends AsyncTask<Void,Void,String> {

    private ActivityFlujo activity;
    private EventoSincronizarRutinas evento = new EventoSincronizarRutinas();
    public SincronizacionRutinasTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {

        if(Network.isOnline(activity))
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String rutinasDTO = gson.toJson(new ServicioRutina().getNoSincronizadas(), new TypeToken<ArrayList<RutinaDTO>>(){}.getType());
            ResponseHelper response = new ServicioRutina().sincronizarAPI(rutinasDTO);
            if(response.getCodigo()==200)
            {
                List<RutinaDTO> rutinasDto = gson.fromJson(response.getMensaje(), new TypeToken<List<RutinaDTO>>(){}.getType());
                new ServicioRutina().sincronizarRutinas(rutinasDto);
            }
            else
            {
                evento.setMensaje("No se pudo sincronizar rutinas.");
            }
        }
        else
        {
            evento.setMensaje("No se pudo sincronizar por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
