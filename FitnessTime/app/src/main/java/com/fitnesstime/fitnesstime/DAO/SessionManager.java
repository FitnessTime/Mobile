package com.fitnesstime.fitnesstime.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;

/**
 * Created by jskalic on 22/04/2016.
 */
public class SessionManager {

    private static SessionManager sessionManager;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private SessionManager() {
        Context context = FitnessTimeApplication.getAppContext();
        FitnessTimeOpenHelper helper = new FitnessTimeOpenHelper(context, Constantes.DATABASE_NAME, null);
        this.db = helper.getWritableDatabase();
        this.daoMaster = new DaoMaster(db);
        this.daoSession = daoMaster.newSession();
    }

    public static SessionManager getInstance() {
        if(sessionManager == null)
            sessionManager = new SessionManager();

        return sessionManager;
    }

    public DaoSession getSession() {
        return this.daoSession;
    }

}
