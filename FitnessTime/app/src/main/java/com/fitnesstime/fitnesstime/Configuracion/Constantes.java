package com.fitnesstime.fitnesstime.Configuracion;

/**
 * Created by julian on 13/02/16.
 */
public class Constantes {

    private static int PASSWORD_LENGTH = 6;
    private static int CODIGO_ERROR_SIN_INTERNET = 555;
    private static int CODIGO_OK = 200;
    public static int FRAGMENT_RUTINA = 0;
    public static int FRAGMENT_HERRAMIENTA = 2;
    public static String CODIGO_HANDLER_SERVICIO_TEMPORIZADOR = "SERVICIO_TEMPORIZADOR";
    public static String DATABASE_NAME = "fitnesstime_db";

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
