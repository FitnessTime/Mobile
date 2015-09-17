package com.fitnesstime.fitnesstime.servicios;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.activity.EjercicioActivity;
import com.fitnesstime.fitnesstime.dominio.Ejercicio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by julian on 22/08/15.
 */
public class EjercicioService extends GenericService<EjercicioActivity> {

    private Gson gson = new Gson();
    private Type typeArray = new TypeToken<ArrayList<Ejercicio>>(){}.getType();

    public EjercicioService Instance(EjercicioActivity a)
    {
        return new EjercicioService(a);
    }

    public EjercicioService(EjercicioActivity activity)
    {
        this.activity = activity;
    }

    public ArrayList<Ejercicio> getEjerciciosDemo()
    {
        Ejercicio ejercicio1 = new Ejercicio();//("Banco plano", 50, 4, 10);
        Ejercicio ejercicio2 = new Ejercicio();//("Banco inclinado", 50, 4, 10);
        ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);

        return ejercicios;
    }

    public ArrayList<Ejercicio> saveEjercicio(EjercicioActivity activity, Ejercicio ejercicio)
    {
        String jsonArray = activity.getSharedPreferences("com.fitnesstime.fitnesstime", Context.MODE_PRIVATE).getString("Ejercicios", null);
        ArrayList<Ejercicio> ejercicios = gson.fromJson(jsonArray,typeArray);
        SharedPreferences.Editor editor = activity.getSharedPreferences("com.fitnesstime.fitnesstime", Context.MODE_PRIVATE).edit();
        ejercicios.add(ejercicio);

        editor.putString("Ejercicios",gson.toJson(ejercicios));
        editor.commit();

        return ejercicios;
    }

    public ArrayList<Ejercicio> getEjercicios(SharedPreferences sp)
    {
        String jsonArray = sp.getString("Ejercicios", null);
        ArrayList<Ejercicio> ejercicios = gson.fromJson(jsonArray,typeArray);
        return  ejercicios;
    }
}
