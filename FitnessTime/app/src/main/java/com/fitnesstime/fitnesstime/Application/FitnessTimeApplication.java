package com.fitnesstime.fitnesstime.Application;

import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Servicios.LogginServicio;
import com.fitnesstime.fitnesstime.Servicios.RegistroServicio;
import com.orm.SugarApp;

import de.greenrobot.event.EventBus;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends SugarApp {

    static LogginServicio logginService;
    static RegistroServicio registroService;

    private static EventBus eventBus =new EventBus();

    public static LogginServicio getLogginServicio() {
        return logginService;
    }
    public static RegistroServicio getRegistroServicio() {
        return registroService;
    }

    static {
        logginService = new LogginServicio();
        registroService = new RegistroServicio();
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
