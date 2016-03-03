package com.fitnesstime.fitnesstime.Eventos;

/**
 * Created by julian on 02/03/16.
 */
public class EventoTemporizador {

    private String tiempo = "";

    public EventoTemporizador(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTiempo(){return tiempo;}
}
