package com.fitnesstime.fitnesstime.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by julian on 27/03/16.
 */
public final class HelperLeerMensajeResponse {

    public static String leerMensaje(InputStream stream) throws Exception
    {
        String line = "";
        String response = "";
        BufferedReader br=new BufferedReader(new InputStreamReader(stream));
        while ((line=br.readLine()) != null) {
            response+=line;
        }
        return response;
    }
}
