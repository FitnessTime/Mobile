package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;

import java.util.List;

/**
 * Created by pablo on 26/11/15.
 */
public abstract class Flujo <Entidad> {
    protected Entidad entidad;
    protected int posicionFragment;
    protected List<Class<? extends ActivityFlujo>> activitys;
    private Integer indice;

    public Flujo() {
        entidad = crearEntidad();
        indice = 0;
    }

    abstract public Entidad getEntidad();

    public Class<? extends ActivityFlujo> siguiente() {
        indice++;
        return activitys.get(indice);
//         return activitys.next();
    }

    public Class<? extends ActivityFlujo> anterior() {
        indice--;
        return activitys.get(indice);
//        return activitys.previous();
    }


    public abstract Entidad crearEntidad();


    public int getPosicionFragment() {
        return posicionFragment;
    }

    public void setPosicionFragment(int posicionFragment) {
        this.posicionFragment = posicionFragment;
    }
}
