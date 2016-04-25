package com.fitnesstime.fitnesstime.Application;

import android.app.Application;
import android.content.Context;

import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Servicios.ServicioLoggin;
import com.fitnesstime.fitnesstime.Servicios.ServicioRegistro;
import com.fitnesstime.fitnesstime.Servicios.ServicioUsuario;

import de.greenrobot.event.EventBus;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends Application {

    static ServicioLoggin logginService;
    static ServicioRegistro registroService;
    static ServicioUsuario servicioUsuario;
    static Context context;
    static SecurityToken session;

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
    public static Context getAppContext() {
        return context;
    }

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
}
