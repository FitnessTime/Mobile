package com.fitnesstime.fitnesstime.dominio;

/**
 * Created by julian on 22/08/15.
 */
public class Ejercicio {

    private String nombre;
    private int peso;
    private int series;
    private int repeticiones;

    public Ejercicio(){
        this.nombre = "";
        this.peso = 0;
        this.series = 0;
        this.repeticiones = 0;
    }

    public Ejercicio(String nombre, int peso, int series, int repeticiones)
    {
        this.nombre = nombre;
        this.peso = peso;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    //******************************* Getters and setters **************************************


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
}
