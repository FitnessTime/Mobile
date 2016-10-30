package com.fitnesstime.fitnesstime.Eventos;

/**
 * Created by julian on 26/06/16.
 */
public class EventoCambiarContrasenia {

    private String mensaje = "Contraseña modificada con éxito.";

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
