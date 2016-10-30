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
        String requestURL = "/login?email=" + email + "&pass=" + password;
        return ServicioRequestHelper.GetRequest(requestURL);
    }

    public ResponseHelper cerrar(SecurityToken st)
    {
        String requestURL = "/close?email=" + st.getEmailUsuario()+ "&authToken=" + st.getAuthToken();
        return ServicioRequestHelper.GetRequest(requestURL);
    }
}
