package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityEjercicio;
import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
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

/**
 * Created by julian on 25/04/16.
 */
public class GuardarRutinaTask extends AsyncTask<String,Void,String> {

    private ActivityFlujo activity;

    public GuardarRutinaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String mensaje = "Rutina creada con exito.";

        if(Network.isOnline(activity))
        {
            ResponseHelper response = new ServicioRutina().guardarAPI(strings[0]);
            if(response.getCodigo()==200)
            {
                Gson gson = new Gson();
                FitnessTimeApplication.getServicioRutina().marcarSincronizada(gson.fromJson(response.getMensaje(), Rutina.class));
                FitnessTimeApplication.getEventBus().post(new EventoActualizar());
            }
        }
        return "";
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);


    }
}
