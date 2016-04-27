package com.fitnesstime.fitnesstime.Eventos;

import com.fitnesstime.fitnesstime.Dominio.SecurityToken;

/**
 * Created by julian on 26/04/16.
 */
public class EventoLoggin {

    private String mensaje = "";
    private String error = "";
    private SecurityToken securityToken;

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

    public SecurityToken getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(SecurityToken securityToken) {
        this.securityToken = securityToken;
    }
}
