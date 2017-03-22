package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Dominio.Paso;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.ServicioPaso;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Date;

/**
 * Created by julian on 19/10/16.
 */
public class GuardarPasosTask extends AsyncTask<Void,Void,String> {

    @Override
    protected String doInBackground(Void... params) {

        Paso paso = new ServicioPaso().getByFecha(new Date());
        if(paso != null)
            new ServicioPaso().guardarAPI(paso);
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
