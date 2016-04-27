package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityLoggin;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Eventos.EventoLoggin;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;

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
        String mensaje = "";

        if(Network.isOnline(activity)) {
            SecurityToken securityToken = FitnessTimeApplication.getLogginServicio().autenticar(strings[0], strings[1]);
            evento.setSecurityToken(securityToken);
            if(securityToken == null)
            {
                evento.setError("Usuario o contraseña invalidos.");
            }
            else
            {
                evento.setMensaje("Usuario " + securityToken.getEmailUsuario() + " loggeado con exito.");
            }
        }
        else
        {
            evento.setError("No tiene conexión a internet.");
        }
        return mensaje;
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        FitnessTimeApplication.getEventBus().post(evento);
    }
}
