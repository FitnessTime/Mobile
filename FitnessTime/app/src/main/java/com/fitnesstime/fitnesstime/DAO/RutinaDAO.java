package com.fitnesstime.fitnesstime.DAO;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Modelo.Ejercicio;
import com.fitnesstime.fitnesstime.Modelo.Rutina;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by julian on 12/04/16.
 */
public class RutinaDAO extends GenericDAO<Rutina>{

    public RutinaDAO()
    {
        super();
    }

    public List<Rutina> getRutinas()
    {
        db.beginTransaction();
        List<Rutina> rutinas = db.where(Rutina.class).equalTo("idUsuario", FitnessTimeApplication.getIdUsuario()).findAll();
        db.commitTransaction();
        return rutinas;
    }

    public List<Ejercicio> getEjerciciosDeRutinas()
    {
        db.beginTransaction();
        List<Rutina> rutinas = db.where(Rutina.class).equalTo("idUsuario", FitnessTimeApplication.getIdUsuario()).findAll();
        List<Ejercicio> ejercicios = new ArrayList<>();
        for(Rutina rutina : rutinas)
        {
            ejercicios.addAll(rutina.getEjercicios());
        }
        db.commitTransaction();
        return ejercicios;
    }

    public List<Ejercicio> getEjerciciosDeRutina(Integer idRutina)
    {
        db.beginTransaction();
        List<Rutina> rutinas = db.where(Rutina.class).equalTo("idUsuario", FitnessTimeApplication.getIdUsuario()).findAll();
        List<Ejercicio> ejercicios = new ArrayList<>();
        for(Rutina rutina : rutinas)
        {
            ejercicios.addAll(rutina.getEjercicios());
        }
        db.commitTransaction();
        return ejercicios;
    }
}
