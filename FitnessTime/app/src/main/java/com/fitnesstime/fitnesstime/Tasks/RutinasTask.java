package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Eventos.EventoRutinas;
import com.fitnesstime.fitnesstime.Servicios.Network;

/**
 * Created by julian on 01/05/16.
 */
public class RutinasTask extends AsyncTask<String,Void,String> {

    private ActivityFlujo activity;
    private EventoRutinas evento = new EventoRutinas();

    public RutinasTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... strings) {
       /* String mensaje = "";

        if(Network.isOnline(activity)) {
        ResponseHelper response = FitnessTimeApplication.getLogginServicio().autenticar(strings[0], strings[1]);
        if(response.getCodigo() == 404)
        {
        evento.setError(response.getMensaje());
        }
        else {
        Gson gson = new Gson();
        SecurityToken securityToken = gson.fromJson(response.getMensaje(), SecurityToken.class);
        evento.setSecurityToken(securityToken);
        evento.setMensaje("Usuario " + securityToken.getEmailUsuario() + " loggeado con exito.");
        }
        }
        else
        {
        evento.setError("No tiene conexión a internet.");
        }
        return mensaje;*/
        return "";
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        //FitnessTimeApplication.getEventBus().post(evento);
    }
}