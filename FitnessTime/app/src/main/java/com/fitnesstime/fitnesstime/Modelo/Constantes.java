package com.fitnesstime.fitnesstime.Modelo;

/**
 * Created by julian on 13/02/16.
 */
public class Constantes {

    private static int PASSWORD_LENGTH = 6;
    private static int CODIGO_ERROR_SIN_INTERNET = 555;
    private static int CODIGO_OK = 200;

    public static int getLongitudContrasenia()
    {
        return PASSWORD_LENGTH;
    }
    public static int getCodigoErrorSinInternet()
    {
        return CODIGO_ERROR_SIN_INTERNET;
    }
    public static int getCodigoOk()
    {
        return CODIGO_OK;
    }
}
