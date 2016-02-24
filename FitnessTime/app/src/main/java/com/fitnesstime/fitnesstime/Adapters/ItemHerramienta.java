package com.fitnesstime.fitnesstime.Adapters;

/**
 * Created by julian on 22/02/16.
 */
public class ItemHerramienta {
    private String nombre;
    private String icono;

    public ItemHerramienta(String name, String icono) {

        nombre = name;
        this.icono = icono;
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
