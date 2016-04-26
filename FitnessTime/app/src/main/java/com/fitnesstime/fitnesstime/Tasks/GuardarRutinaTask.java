package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityEjercicio;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;

/**
 * Created by julian on 25/04/16.
 */
public class GuardarRutinaTask extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {
        String mensaje = "";

            SecurityToken securityToken = FitnessTimeApplication.getLogginServicio().autenticar(strings[0], strings[1]);
            if(securityToken == null)
                mensaje = "Usuario o contraseña invalidos.";
            else
            {
                mensaje = "Usuario " + securityToken.getEmailUsuario() + " loggeado con exito.";
                new ServicioSecurityToken().guardar(securityToken);
                FitnessTimeApplication.setSession(securityToken);
            }

        return mensaje;
    }
    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        //Toast toast = Toast.makeText(ActivityLoggin.this, string, Toast.LENGTH_SHORT);
        //View view = toast.getView();
        //view.setBackgroundResource(R.color.boton_loggin);
        //toast.show();
        //activarCampos();
        //desactivarSpinner();
    }
}
