package com.fitnesstime.fitnesstime.Configuracion;

/**
 * Created by julian on 13/02/16.
 */
public class Constantes {

    private static int PASSWORD_LENGTH = 6;
    private static int CODIGO_ERROR_SIN_INTERNET = 555;
    private static int CODIGO_OK = 200;
    public static int FRAGMENT_RUTINA = 0;
    public static int FRAGMENT_EJERCICIOS = 1;
    public static int FRAGMENT_HERRAMIENTA = 2;
    public static int FRAGMENT_ESTADISTICAS = 3;
    public static String CODIGO_HANDLER_SERVICIO_TEMPORIZADOR = "SERVICIO_TEMPORIZADOR";
    public static String DATABASE_NAME = "fitnesstime_db";
    public static String URL_API = "http://api-fitnesstime.herokuapp.com";
    public static int CRON_DELAY_SINRONIZAR_RUTINAS = 1800000;
    public static int CRON_DELAY_GUARDAR_PASOS = 900000;
    public static int JOB_SINCRONIZAR_RUTINAS_ID = 2458;
    public static int JOB_GUARDAR_PASOS_ID = 2459;

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
