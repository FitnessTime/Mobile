package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizarRutina;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarRutina;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by julian on 09/05/16.
 */
public class EditarRutinaTask extends AsyncTask<Rutina,Void,String> {

    private ActivityFlujo activity;
    private EventoActualizarRutina evento = new EventoActualizarRutina();
    public EditarRutinaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Rutina... rutinas) {

        if(Network.isOnline(activity))
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String param = gson.toJson(RutinaAssembler.toDTO(rutinas[0]), RutinaDTO.class);
            param = param.replace(" ", "%20");
            ResponseHelper response = new ServicioRutina().editarAPI(param);
            if(response.getCodigo()==200)
            {
                new ServicioRutina().actualizar(gson.fromJson(response.getMensaje(), RutinaDTO.class));
            }
            else
            {
                evento.setMensaje("Rutina actualizada, pero no sincronizada.");
            }
        }
        else
        {
            evento.setMensaje("Rutina modificada, pero no sincronizado por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
