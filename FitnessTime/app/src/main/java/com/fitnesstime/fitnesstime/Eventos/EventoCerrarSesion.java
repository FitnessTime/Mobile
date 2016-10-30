package com.fitnesstime.fitnesstime.Eventos;

/**
 * Created by julian on 26/06/16.
 */
public class EventoCerrarSesion {

    private String mensaje = "Gracias por usar fitness time.";

    private int code = 200;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
