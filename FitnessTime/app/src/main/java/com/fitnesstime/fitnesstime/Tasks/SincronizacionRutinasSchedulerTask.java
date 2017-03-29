package com.fitnesstime.fitnesstime.Tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Eventos.EventoSincronizarRutinas;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 22/06/16.
 */
public class SincronizacionRutinasSchedulerTask extends AsyncTask<Void,Void,String> {

    private EventoSincronizarRutinas evento = new EventoSincronizarRutinas();

    @Override
    protected String doInBackground(Void... params) {

        if(!new ServicioSecurityToken().estaAutenticado())
            return "";
        Gson gson = new GsonBuilder().serializeNulls().create();
        String rutinasDTO = gson.toJson(new ServicioRutina().getNoSincronizadas(), new TypeToken<ArrayList<RutinaDTO>>(){}.getType());
        rutinasDTO = rutinasDTO.replace(" ", "%20");
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

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
