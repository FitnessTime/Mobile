package com.fitnesstime.fitnesstime.Servicios;

import android.content.Context;
import android.util.Log;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 25/01/16.
 */
public class ServicioLoggin {

    public SecurityToken autenticar(String email, String password)
    {
        SecurityToken securityToken = null;
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/login?email=" + email + "&pass=" + password);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            int code = urlConnection.getResponseCode();
            if(code == 404)
            {
                securityToken = null;
            }
            else {
                Gson gson = new Gson();
                securityToken = gson.fromJson(HelperLeerMensajeResponse.leerMensaje(urlConnection), SecurityToken.class);
            }
        }catch(Exception e)
        {
            Log.println(1,"",e.getMessage());
        }
        return securityToken;
    }

    public int cerrar(SecurityToken st)
    {
        int code = 500;
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/close?email=" + st.getEmailUsuario()+ "&authToken=" + st.getAuthToken());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            code = urlConnection.getResponseCode();
        }catch(Exception e)
        {
            Log.println(1,"",e.getMessage());
        }
        return code;
    }
}
