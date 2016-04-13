package com.fitnesstime.fitnesstime.DAO;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Modelo.Rutina;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by julian on 12/04/16.
 */
public class GenericDAO<T extends RealmObject> {

    protected Realm db;

    public GenericDAO()
    {
        db = new FitnessTimeApplication().getDB();
    }

    public void crear(T entidad)
    {
        db.beginTransaction();
        db.copyToRealm(entidad);
        db.commitTransaction();
    }

    public void borrar(T entidad)
    {
        db.beginTransaction();
        entidad.removeFromRealm();
        db.commitTransaction();
    }
}
