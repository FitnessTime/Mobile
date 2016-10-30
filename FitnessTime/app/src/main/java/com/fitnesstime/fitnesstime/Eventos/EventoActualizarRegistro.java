package com.fitnesstime.fitnesstime.Eventos;

/**
 * Created by julian on 25/06/16.
 */
public class EventoActualizarRegistro {

    private String mensaje = "";
    private String error = "";

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
