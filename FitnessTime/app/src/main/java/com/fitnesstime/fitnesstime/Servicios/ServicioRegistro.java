package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 08/02/16.
 */
public class ServicioRegistro {

    public int registrar(Registro registro)
    {
        int code = 500;
        try {
            Gson gson = new Gson();
            URL url = new URL("http://api-fitnesstime.herokuapp.com/registrarUsuario?usuario=" + gson.toJson(registro, Registro.class));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            code = urlConnection.getResponseCode();
            code = code!=200? 500 : 200;
        }catch(Exception e)
        {
            Log.println(1, "", e.getMessage());
        }
        return code;
    }
}
