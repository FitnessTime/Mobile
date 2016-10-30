package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 27/03/16.
 */
public class ServicioUsuario {


    public static ResponseHelper cambiarContrasenia(String nuevaPass)
    {
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/cambiarContrasenia?"
                              + "email=" + FitnessTimeApplication.getSession().getEmailUsuario()
                              + "&authToken=" + FitnessTimeApplication.getSession().getAuthToken()
                              + "&nuevaPass=" + nuevaPass);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("PUT");
            int code = urlConnection.getResponseCode();
            InputStream stream = code!=200?urlConnection.getErrorStream():urlConnection.getInputStream();
            String response = HelperLeerMensajeResponse.leerMensaje(stream);
            return new ResponseHelper(code,response);
        }catch(Exception e)
        {
            return new ResponseHelper(404,"Error " + e.getMessage());
        }
    }
}
