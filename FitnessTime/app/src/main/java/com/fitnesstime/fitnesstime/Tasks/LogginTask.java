package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityLoggin;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Eventos.EventoLoggin;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.google.gson.Gson;

/**
 * Created by julian on 26/04/16.
 */
public class LogginTask extends AsyncTask<String,Void,String> {

    private ActivityFlujo activity;
    private EventoLoggin evento = new EventoLoggin();

    public LogginTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {

        if(Network.isOnline(activity)) {
            ResponseHelper response = FitnessTimeApplication.getLogginServicio().autenticar(strings[0], strings[1]);
            if(response.getCodigo() == 200)
            {
                Gson gson = new Gson();
                SecurityToken securityToken = gson.fromJson(response.getMensaje(), SecurityToken.class);
                evento.setSecurityToken(securityToken);
                evento.setMensaje("Usuario " + securityToken.getEmailUsuario() + " loggeado con exito.");
            }
            else {
                evento.setError(response.getMensaje());
            }
        }
        else
        {
            evento.setError("No tiene conexión a internet.");
        }
        return "";
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
