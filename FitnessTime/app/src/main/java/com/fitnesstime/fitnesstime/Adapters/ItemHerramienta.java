package com.fitnesstime.fitnesstime.Adapters;

import android.support.v7.widget.CardView;
import android.view.View;

/**
 * Created by julian on 22/02/16.
 */
public class ItemHerramienta {
    private String nombre;
    private String icono;
    private View.OnClickListener accion;

    public ItemHerramienta(String name, String icono, View.OnClickListener accion) {

        nombre = name;
        this.icono = icono;
        this.accion = accion;
    }

    public View.OnClickListener getAccion() {
        return accion;
    }

    public void setAccion(View.OnClickListener accion) {
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
}
