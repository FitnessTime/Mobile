package com.fitnesstime.fitnesstime.Tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityEjercicio;
import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizar;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarRutina;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by julian on 25/04/16.
 */
public class GuardarRutinaTask extends AsyncTask<Rutina,Void,String> {

    private ActivityFlujo activity;
    private EventoGuardarRutina evento = new EventoGuardarRutina();
    public GuardarRutinaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Rutina... rutinas) {

        if(Network.isOnline(activity))
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String param = gson.toJson(RutinaAssembler.toDTO(rutinas[0]), RutinaDTO.class);
            ResponseHelper response = new ServicioRutina().guardarAPI(param);
            if(response.getCodigo()==200)
            {
                new ServicioRutina().actualizar(gson.fromJson(response.getMensaje(), RutinaDTO.class));
            }
            else
            {
                evento.setMensaje("Rutina creada, pero no sincronizada");
            }
        }
        else
        {
            evento.setMensaje("Rutina creada, pero no sincronizada por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
