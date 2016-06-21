package com.fitnesstime.fitnesstime.Adapters;

import android.view.View;

/**
 * Created by julian on 19/06/16.
 */
public class ItemEstadistica {

    private String nombre;
    private String icono;
    private View.OnClickListener accion;

    public ItemEstadistica(String name, String icono, View.OnClickListener accion) {

        nombre = name;
        this.icono = icono;
        this.accion = accion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public View.OnClickListener getAccion() {
        return accion;
    }

    public void setAccion(View.OnClickListener accion) {
        this.accion = accion;
    }
}
