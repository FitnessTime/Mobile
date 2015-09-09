package com.fitnesstime.fitnesstime.dominio;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by julian on 24/08/15.
 */
public class Rutina {

    private String objetivo;
    private Date inicio;
    private Date fin;
    private ArrayList<Ejercicio> ejercicios;

    public Rutina()
    {
        this.objetivo =  "";
        this.inicio = new Date();
        this.fin = new Date(2015,10,5);
        this.ejercicios = new ArrayList<Ejercicio>();
    }

    public Rutina(String objetivo, Date inicio, Date fin)
    {
        this.objetivo = objetivo;
        this.inicio = inicio;
        this.fin = fin;
        this.ejercicios = new ArrayList<Ejercicio>();
    }

    //***************************** Getters and setters ****************************************


    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
