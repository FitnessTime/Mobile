package com.fitnesstime.fitnesstime.Application;

import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Servicios.LogginServicio;
import com.fitnesstime.fitnesstime.Servicios.RegistroServicio;
import com.orm.SugarApp;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends SugarApp {

    static LogginServicio logginService;
    static RegistroServicio registroService;

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

}
