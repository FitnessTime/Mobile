package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Gson gson = new GsonBuilder().serializeNulls().create();
        String requestURL = "/usuarios?usuario=" + gson.toJson(registro, Registro.class);
        return ServicioRequestHelper.PostRequest(requestURL);
    }

    public ResponseHelper actualizar(Registro registro)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String param = gson.toJson(registro, Registro.class);
        param = param.replace(" ", "%20");
        String requestURL = "/usuarios?authToken=" + FitnessTimeApplication.getSession().getAuthToken() +"&usuario=" + param;
        return ServicioRequestHelper.PutRequest(requestURL);
    }
}
