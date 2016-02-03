package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 25/01/16.
 */
public class LogginServicio {

    public SecurityToken autenticar(String email, String password)
    {
        SecurityToken securityToken = new SecurityToken();
        try {
            URL url = new URL("http://api-fitnesstime.herokuapp.com/autenticar?email=" + email + "&pass=" + password);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            StringBuilder jsonResults = new StringBuilder();

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            Gson gson = new Gson();
            securityToken = gson.fromJson(jsonResults.toString(),SecurityToken.class);
        }catch(Exception e)
        {
            Log.println(1,"",e.getMessage());
        }
        return securityToken;
    }
}
