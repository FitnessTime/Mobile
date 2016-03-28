package com.fitnesstime.fitnesstime.Modelo;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;

/**
 * Created by julian on 14/02/16.
 */
public class ResponseHelper {

    private int codigo;
    private String mensaje;

    public ResponseHelper(int codigo, String msj)
    {
        this.codigo = codigo;
        this.mensaje = msj;
    }

    public static String getMensajeDelResponse(int codigo)
    {
        String mensaje = "Error del servidor.";
        if(codigo == 410)
            mensaje = "Ya existe un usuario con esta cuenta.";
        if(codigo == 411)
            mensaje = "No se pudo registrar el usuario.";
        if(codigo == 200)
            mensaje = "Usuario registrado.";
        if(codigo == Constantes.getCodigoErrorSinInternet())
            mensaje = "No tiene conexion a internet";
        return mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
