package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 08/02/16.
 */
public class RegistroServicio {

    public int registrar(String email, String password, String nombre, String fecha, int peso)
    {
        int code = 0;
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/registrar?email="+email+"&pass="+password+"&nombre="+nombre+"&fecha="+fecha+"&peso=" + String.valueOf(peso));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            code = urlConnection.getResponseCode();

        }catch(Exception e)
        {
            Log.println(1, "", e.getMessage());
        }
        return code;
    }
}
