package com.fitnesstime.fitnesstime.Tasks;

import android.os.AsyncTask;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Assemblers.MarcaAssembler;
import com.fitnesstime.fitnesstime.DTOs.MarcaDTO;
import com.fitnesstime.fitnesstime.Dominio.Marca;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioMarca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by julian on 26/06/16.
 */
public class GuardarMarcaTask extends AsyncTask<Marca,Void,String> {

    private ActivityFlujo activity;
    public GuardarMarcaTask(ActivityFlujo activity)
    {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Marca... marcas) {

        if(Network.isOnline(activity))
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String param = gson.toJson(MarcaAssembler.toDTO(marcas[0]), MarcaDTO.class);
            param = param.replace(" ", "%20");
            ResponseHelper response = new ServicioMarca().guardarAPI(param);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
