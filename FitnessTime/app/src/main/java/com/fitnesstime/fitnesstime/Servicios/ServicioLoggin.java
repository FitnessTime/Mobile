package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
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
}
