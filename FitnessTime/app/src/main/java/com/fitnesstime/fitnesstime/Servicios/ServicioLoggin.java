package com.fitnesstime.fitnesstime.Servicios;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 25/01/16.
 */
public class ServicioLoggin {

    public ResponseHelper autenticar(String email, String password)
    {
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/login?email=" + email + "&pass=" + password);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");
            //urlConnection.setDoInput(true);
            //urlConnection.setDoOutput(true);
            int code = urlConnection.getResponseCode();
            InputStream stream = code!=200?urlConnection.getErrorStream():urlConnection.getInputStream();
            String response = HelperLeerMensajeResponse.leerMensaje(stream);
            return new ResponseHelper(code,response);

        }catch(Exception e)
        {
            return new ResponseHelper(404,"Time out.");
        }
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
