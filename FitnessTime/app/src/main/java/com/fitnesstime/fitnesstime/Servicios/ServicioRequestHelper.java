package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by julian on 07/10/16.
 */
public class ServicioRequestHelper {

    // GET request a url determinada
    public static ResponseHelper GetRequest(String requestURL)
    {
        return Request("GET", requestURL);
    }

    // PUT request a url determinada
    public static ResponseHelper PutRequest(String requestURL)
    {
        return Request("PUT", requestURL);
    }

    // POST request a url determinada
    public static ResponseHelper PostRequest(String requestURL)
    {
        return Request("POST", requestURL);
    }

    // DELETE request a url determinada
    public static ResponseHelper DeleteRequest(String requestURL)
    {
        return Request("DELETE", requestURL);
    }

    // Request generico segun tipo: GET, PUT, DELETE, POST.
    private static ResponseHelper Request(String tipoRequest, String requestURL)
    {
        try {
            URL url = new URL(Constantes.URL_API + requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod(tipoRequest);
            int code = urlConnection.getResponseCode();
            InputStream stream = code!=200?urlConnection.getErrorStream():urlConnection.getInputStream();
            String response = HelperLeerMensajeResponse.leerMensaje(stream);
            return new ResponseHelper(code,response);
        }catch(Exception e)
        {
            return new ResponseHelper(404,"Error del servidor, intente mas tarde");
        }
    }
}
