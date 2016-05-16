package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 08/02/16.
 */
public class ServicioRegistro {

    public ResponseHelper registrar(Registro registro)
    {
        try {
            Gson gson = new Gson();
            URL url = new URL("http://api-fitnesstime.herokuapp.com/registrarUsuario?usuario=" + gson.toJson(registro, Registro.class));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(3000);
            int code = urlConnection.getResponseCode();
            InputStream stream = code!=200?urlConnection.getErrorStream():urlConnection.getInputStream();
            String response = HelperLeerMensajeResponse.leerMensaje(stream);
            return new ResponseHelper(code,response);
        }catch(Exception e)
        {
            return new ResponseHelper(404,"Error: " + e.getMessage());
        }
    }
}
