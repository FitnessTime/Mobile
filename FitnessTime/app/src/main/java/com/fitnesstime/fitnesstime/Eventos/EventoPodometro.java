package com.fitnesstime.fitnesstime.Eventos;

/**
 * Created by julian on 02/10/16.
 */
public class EventoPodometro {

    private int cantidadPasos = 0;

    public EventoPodometro(int pasos)
    {
        this.cantidadPasos = pasos;
    }

    public int getCantidadPasos() {
        return cantidadPasos;
    }

    public void setCantidadPasos(int cantidadPasos) {
        this.cantidadPasos = cantidadPasos;
    }
}
