package com.fitnesstime.fitnesstime.Modelo;

import com.orm.SugarRecord;

/**
 * Created by julian on 25/01/16.
 */
public class SecurityToken extends SugarRecord<SecurityToken> {

    private int idUsuario;
    private String nombreUsuario;
    private String emailUsuario;
    private String authToken;

    public SecurityToken()
    {
        authToken = "";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}


