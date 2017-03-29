package com.fitnesstime.fitnesstime.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.fitnesstime.fitnesstime.Dominio.Paso;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PASO".
*/
public class PasoDao extends AbstractDao<Paso, Long> {

    public static final String TABLENAME = "PASO";

    /**
     * Properties of entity Paso.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IdUsuario = new Property(1, String.class, "idUsuario", false, "ID_USUARIO");
        public final static Property PasosDados = new Property(2, Float.class, "pasosDados", false, "PASOS_DADOS");
        public final static Property Fecha = new Property(3, String.class, "fecha", false, "FECHA");
    };


    public PasoDao(DaoConfig config) {
        super(config);
    }
    
    public PasoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PASO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ID_USUARIO\" TEXT NOT NULL ," + // 1: idUsuario
                "\"PASOS_DADOS\" REAL," + // 2: pasosDados
                "\"FECHA\" TEXT);"); // 3: fecha
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PASO\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Paso entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getIdUsuario());
 
        Integer pasosDados = entity.getPasosDados();
        if (pasosDados != null) {
            stmt.bindDouble(3, pasosDados);
        }
 
        String fecha = entity.getFecha();
        if (fecha != null) {
            stmt.bindString(4, fecha);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Paso readEntity(Cursor cursor, int offset) {
        Paso entity = new Paso( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // idUsuario
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // pasosDados
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // fecha
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Paso entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdUsuario(cursor.getString(offset + 1));
        entity.setPasosDados(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setFecha(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Paso entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Paso entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}