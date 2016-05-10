package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizar;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarRutina;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.google.gson.Gson;

/**
 * Created by julian on 09/05/16.
 */
public class EditarRutinaTask extends AsyncTask<Rutina,Void,String> {

    private ActivityFlujo activity;

    public EditarRutinaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Rutina... rutinas) {
        String mensaje = "Rutina modificada con exito.";

        if(Network.isOnline(activity))
        {
            Gson gson = new Gson();
            String param = gson.toJson(RutinaAssembler.toDTO(rutinas[0]), RutinaDTO.class);
            ResponseHelper response = new ServicioRutina().guardarAPI(param);
            if(response.getCodigo()==200)
            {
                new ServicioRutina().actualizar(gson.fromJson(response.getMensaje(), RutinaDTO.class));
                FitnessTimeApplication.getEventBus().post(new EventoActualizar());
            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(new EventoGuardarRutina());
    }
}
