package com.fitnesstime.fitnesstime.Application;

import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Servicios.LogginServicio;
import com.orm.SugarApp;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends SugarApp {

    static LogginServicio logginService;

    public static LogginServicio getLogginServicio() {
        return logginService;
    }

    static {
        logginService = new LogginServicio();
    }

    private Flujo flujo;

    public void setFlujo(Flujo flujo ){ this.flujo = flujo;}
    public Flujo getFlujo(){
        return flujo;
    }

}
