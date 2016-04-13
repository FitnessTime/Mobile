package com.fitnesstime.fitnesstime.Application;

import android.app.Application;

import com.fitnesstime.fitnesstime.DAO.SecurityTokenDAO;
import com.fitnesstime.fitnesstime.Flujos.Flujo;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Servicios.ServicioLoggin;
import com.fitnesstime.fitnesstime.Servicios.ServicioRegistro;
import com.fitnesstime.fitnesstime.Servicios.ServicioUsuario;
import com.orm.SugarApp;
import com.orm.SugarRecord;

import java.util.Iterator;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by julian on 25/01/16.
 */
public class FitnessTimeApplication extends Application {

    static ServicioLoggin logginService;
    static ServicioRegistro registroService;
    static ServicioUsuario servicioUsuario;

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

    public Realm getDB() {

        return Realm.getDefaultInstance();
    }

    public static String getIdUsuario()
    {
        return new SecurityTokenDAO().getSecurityToken().getEmailUsuario();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // The realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }

}
