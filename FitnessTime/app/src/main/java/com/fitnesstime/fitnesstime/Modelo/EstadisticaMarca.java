package com.fitnesstime.fitnesstime.Modelo;

import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Marca;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 15/06/16.
 */
public class EstadisticaMarca {

    private Ejercicio ejercicio;
    private List<Marca> marcas = new ArrayList<>();

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }
}
