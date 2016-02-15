package com.fitnesstime.fitnesstime.Modelo;

/**
 * Created by julian on 14/02/16.
 */
public class ResponseHelper {

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
}
