package com.fitnesstime.fitnesstime.servicios;

import com.fitnesstime.fitnesstime.dominio.Ejercicio;
import com.fitnesstime.fitnesstime.dominio.Rutina;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by julian on 24/08/15.
 */
public class RutinaService {

    public Rutina GetRutina()
    {
        Rutina rutina = new Rutina("Nada", new Date(), new Date(2015,10,10));
        Ejercicio ejercicio1 = new Ejercicio();//("Banco plano", 50, 4, 10);
        Ejercicio ejercicio2 = new Ejercicio();//("Banco inclinado", 50, 4, 10);
        ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

        ejercicios.add(ejercicio1);
        ejercicios.add(ejercicio2);

        rutina.setEjercicios(ejercicios);

        return rutina;
    }
}
