package com.fitnesstime.fitnesstime.Application;

import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Servicios.ServicioLoggin;
import com.fitnesstime.fitnesstime.Servicios.ServicioRegistro;
import com.fitnesstime.fitnesstime.Servicios.ServicioUsuario;
import com.orm.SugarApp;

import de.greenrobot.event.EventBus;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends SugarApp {

    static ServicioLoggin logginService;
    static ServicioRegistro registroService;
    static ServicioUsuario servicioUsuario;

    private static EventBus eventBus =new EventBus();

    public static ServicioLoggin getLogginServicio() {
        return logginService;
    }
    public static ServicioRegistro getRegistroServicio() {
        return registroService;
    }
    public static ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
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

}
