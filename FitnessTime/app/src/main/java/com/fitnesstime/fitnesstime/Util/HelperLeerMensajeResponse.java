package com.fitnesstime.fitnesstime.Util;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by julian on 27/03/16.
 */
public final class HelperLeerMensajeResponse {

    public static String leerMensaje(HttpURLConnection urlConnection) throws Exception
    {
        InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
        StringBuilder jsonResults = new StringBuilder();
        int read;
        char[] buff = new char[1024];
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
        return jsonResults.toString();
    }
}
