package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 27/03/16.
 */
public class ServicioUsuario {


    public static ResponseHelper cambiarContrasenia(String emailUsuario, String authToken, String nuevaPass)
    {
        ResponseHelper rs = new ResponseHelper(404, "");
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/cambiarContrasenia?"
                              + "email=" + emailUsuario
                              + "&authToken=" + authToken
                              + "&nuevaPass=" + nuevaPass);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            int code = urlConnection.getResponseCode();
            String m = urlConnection.getResponseMessage();
            String msj = HelperLeerMensajeResponse.leerMensaje(urlConnection);
            rs.setCodigo(code);
            rs.setMensaje(msj);
        }catch(Exception e)
        {
            rs.setMensaje("Time out del servidor, intente nuevamente.");
        }
        return rs;
    }
}
