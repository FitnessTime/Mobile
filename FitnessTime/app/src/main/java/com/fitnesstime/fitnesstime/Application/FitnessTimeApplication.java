package com.fitnesstime.fitnesstime.Application;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Servicios.ServicioLoggin;
import com.fitnesstime.fitnesstime.Servicios.ServicioRegistro;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Servicios.ServicioUsuario;

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
        return session.getEmailUsuario();
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
    }

    public static boolean isEjecutandoTarea() {
        return ejecutandoTarea;
    }

    public static void setEjecutandoTarea(boolean ejecutandoTareaa) {
        ejecutandoTarea = ejecutandoTareaa;
    }

    public static void activarProgressDialog(ActivityFlujo activity, String mensaje)
    {
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
}
