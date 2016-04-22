package com.fitnesstime.fitnesstime.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by jskalic on 22/04/2016.
 */
public class FitnessTimeOpenHelper extends DaoMaster.OpenHelper {

    public FitnessTimeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("BacOpenHelper", "Actualizando esquema desde versión " + Integer.toString(oldVersion) + " a " + Integer.toString(newVersion) + ".");

        switch (oldVersion) {
            case 1:
                /* v1->v2: aquí se deben registrar todos los cambios realizados en la versión 2. */
        }
    }

}