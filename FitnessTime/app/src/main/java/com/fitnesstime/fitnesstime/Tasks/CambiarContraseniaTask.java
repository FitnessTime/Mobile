package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Eventos.EventoCambiarContrasenia;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioUsuario;

/**
 * Created by julian on 26/06/16.
 */
public class CambiarContraseniaTask extends AsyncTask<String,Void,String> {

    private ActivityFlujo activity;
    private EventoCambiarContrasenia evento = new EventoCambiarContrasenia();
    public CambiarContraseniaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        if(Network.isOnline(activity))
        {
            String param = params[0].replace(" ", "%20");
            ResponseHelper response = new ServicioUsuario().cambiarContrasenia(param);
            evento.setCode(response.getCodigo());
            if(response.getCodigo()!=200)
            {
                evento.setMensaje("No se pudo modificar la contraseña.");
            }
        }
        else
        {
            evento.setMensaje("No se pudo modificar la contraseña por falta de conexión.");
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}