package com.fitnesstime.fitnesstime.Application;

import android.app.Application;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Jobs.GuardarPasosJob;
import com.fitnesstime.fitnesstime.Jobs.SincronizarSchedulerService;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioLoggin;
import com.fitnesstime.fitnesstime.Servicios.ServicioPaso;
import com.fitnesstime.fitnesstime.Servicios.ServicioPodometro;
import com.fitnesstime.fitnesstime.Servicios.ServicioRegistro;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.fitnesstime.fitnesstime.Servicios.ServicioUsuario;
import com.fitnesstime.fitnesstime.Util.HelperConnectivity;

import de.greenrobot.event.EventBus;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends Application {

    static ServicioLoggin logginService;
    static ServicioRegistro registroService;
    static ServicioUsuario servicioUsuario;
    static ServicioRutina servicioRutina;
    static Context context;
    static SecurityToken session;
    static ProgressDialog dialog;
    static boolean ejecutandoTarea = false;
    static String mensajeTareaEnEjecucion = "";
    static boolean ejecutandoServicioDistanciaRecorrida = false;
    static int posicionFragmentVerEjercicios = 0;
    static int posicionActivityPrincipal = 0;

    private static EventBus eventBus = new EventBus();

    public static ServicioLoggin getLogginServicio() {
        return logginService;
    }
    public static ServicioRegistro getRegistroServicio() {
        return registroService;
    }
    public static ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
    }
    public static ServicioRutina getServicioRutina() {
        return servicioRutina;
    }
    public static Context getAppContext() {
        return context;
    }
    public static ProgressDialog getProgressDialog() { return dialog; }

    static {
        logginService = new ServicioLoggin();
        registroService = new ServicioRegistro();
        servicioUsuario = new ServicioUsuario();
    }

    private Flujo flujo;

    public void setFlujo(Flujo flujo ){ this.flujo = flujo;}
    public Flujo getFlujo(){
        return flujo;
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static String getIdUsuario()
    {
        if(session == null)
            setSession(new ServicioSecurityToken().getAll().get(0));
        return session.getEmailUsuario();
    }

    public static int getPasosMinimosUsuario()
    {
        if(session == null)
            setSession(new ServicioSecurityToken().getAll().get(0));
        return session.getCantidadMinimaPasosUsuario() != "" ? Integer.parseInt(session.getCantidadMinimaPasosUsuario()) : 0;
    }

    public static SecurityToken getSession() {
        return session;
    }

    public static void setSession(SecurityToken session) {
        FitnessTimeApplication.session = session;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        FitnessTimeApplication.context = getApplicationContext();
        CorrerServicioSincronizarRutinas();
        CorrerServicioGuardarPasos();
        startService(new Intent(getBaseContext(), ServicioPodometro.class));
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        TerminarServicios();
    }

    public void TerminarServicios()
    {
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(Constantes.JOB_GUARDAR_PASOS_ID);
        StopServicioPodometro();
    }

    public void StopServicioPodometro()
    {

        stopService(new Intent(getBaseContext(), ServicioPodometro.class));
    }

    // Servicio que corre cada determinado cron, para sincronizar las rutinas automaticamente
    private void CorrerServicioGuardarPasos()
    {
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getApplicationContext(), GuardarPasosJob.class);
        JobInfo jobInfo = new JobInfo.Builder(Constantes.JOB_GUARDAR_PASOS_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(true)
                .setPeriodic(Constantes.CRON_DELAY_GUARDAR_PASOS)
                .build();
        jobScheduler.schedule(jobInfo);
    }

    // Servicio que corre cada determinado cron, para sincronizar las rutinas automaticamente
    private void CorrerServicioSincronizarRutinas()
    {
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getApplicationContext(), SincronizarSchedulerService.class);
        JobInfo jobInfo = new JobInfo.Builder(Constantes.JOB_SINCRONIZAR_RUTINAS_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(true)
                .setPeriodic(Constantes.CRON_DELAY_SINRONIZAR_RUTINAS)
                .build();
        jobScheduler.schedule(jobInfo);
    }

    public static boolean isEjecutandoTarea() {
        return ejecutandoTarea;
    }

    public static void setEjecutandoTarea(boolean ejecutandoTareaa) {
        ejecutandoTarea = ejecutandoTareaa;
    }

    public static boolean isEjecutandoServicioDistanciaRecorrida() {
        return ejecutandoServicioDistanciaRecorrida;
    }

    public static void setEjecutandoServicioDistanciaRecorrida(boolean ejecutandoServicioDistanciaRecorrida) {
        FitnessTimeApplication.ejecutandoServicioDistanciaRecorrida = ejecutandoServicioDistanciaRecorrida;
    }

    public static void activarProgressDialog(ActivityFlujo activity, String mensaje)
    {
        mensajeTareaEnEjecucion = mensaje;
        dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(mensaje);
        dialog.show();
    }

    public static boolean mostrandoDialog()
    {
        if(dialog!=null)
            return dialog.isShowing();
        else
            return false;
    }

    public static void mostrarDialog()
    {
        dialog.show();
    }

    public static void desactivarProgressDialog()
    {
        if(dialog != null)
            dialog.dismiss();
    }

    public static String getMensajeTareaEnEjecucion() {
        return mensajeTareaEnEjecucion;
    }

    public static void setMensajeTareaEnEjecucion(String mensajeTareaEnEjecucion) {
        FitnessTimeApplication.mensajeTareaEnEjecucion = mensajeTareaEnEjecucion;
    }

    public static int getPosicionFragmentVerEjercicios() {
        return posicionFragmentVerEjercicios;
    }

    public static void setPosicionFragmentVerEjercicios(int posicionFragmentVerEjercicios) {
        FitnessTimeApplication.posicionFragmentVerEjercicios = posicionFragmentVerEjercicios;
    }

    public static int getPosicionActivityPrincipal() {
        return posicionActivityPrincipal;
    }

    public static void setPosicionActivityPrincipal(int posicionActivityPrincipal) {
        FitnessTimeApplication.posicionActivityPrincipal = posicionActivityPrincipal;
    }
}
