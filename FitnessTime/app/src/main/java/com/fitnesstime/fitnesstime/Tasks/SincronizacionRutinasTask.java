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
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julian on 01/05/16.
 */
public class SincronizacionRutinasTask extends AsyncTask<Void,Void,String> {

    private ActivityFlujo activity;

    public SincronizacionRutinasTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        String mensaje = "Rutina modificada con exito.";

        if(Network.isOnline(activity))
        {
            Gson gson = new Gson();
            String rutinasDTO = gson.toJson(new ServicioRutina().getNoSincronizadas(), new TypeToken<ArrayList<RutinaDTO>>(){}.getType());
            ResponseHelper response = new ServicioRutina().sincronizarAPI(rutinasDTO);
            if(response.getCodigo()==200)
            {
                List<String> rutinas = gson.fromJson(response.getMensaje(), List.class);
                List<RutinaDTO> rutinasDto = new ArrayList<>();
                for(String json : rutinas)
                {
                    rutinasDto.add(gson.fromJson(json, RutinaDTO.class));
                }
                new ServicioRutina().sincronizarRutinas(rutinasDto);
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
