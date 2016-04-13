package com.fitnesstime.fitnesstime.Modelo;

import com.orm.SugarRecord;

import io.realm.RealmObject;

/**
 * Created by julian on 25/01/16.
 */
public class SecurityToken extends RealmObject {

    private String nombreUsuario;
    private String emailUsuario;
    private String authToken;

    public SecurityToken()
    {
        authToken = "";
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


