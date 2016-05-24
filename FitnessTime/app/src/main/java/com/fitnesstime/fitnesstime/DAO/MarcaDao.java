package com.fitnesstime.fitnesstime.DAO;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.fitnesstime.fitnesstime.Dominio.Marca;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MARCA".
*/
public class MarcaDao extends AbstractDao<Marca, Long> {

    public static final String TABLENAME = "MARCA";

    /**
     * Properties of entity Marca.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Fecha = new Property(1, String.class, "fecha", false, "FECHA");
        public final static Property Carga = new Property(2, Integer.class, "carga", false, "CARGA");
        public final static Property EjercicioId = new Property(3, long.class, "ejercicioId", false, "EJERCICIO_ID");
    };

    private Query<Marca> ejercicio_MarcaListQuery;

    public MarcaDao(DaoConfig config) {
        super(config);
    }
    
    public MarcaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MARCA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"FECHA\" TEXT," + // 1: fecha
                "\"CARGA\" INTEGER," + // 2: carga
                "\"EJERCICIO_ID\" INTEGER NOT NULL );"); // 3: ejercicioId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MARCA\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Marca entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fecha = entity.getFecha();
        if (fecha != null) {
            stmt.bindString(2, fecha);
        }
 
        Integer carga = entity.getCarga();
        if (carga != null) {
            stmt.bindLong(3, carga);
        }
        stmt.bindLong(4, entity.getEjercicioId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Marca readEntity(Cursor cursor, int offset) {
        Marca entity = new Marca( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fecha
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // carga
            cursor.getLong(offset + 3) // ejercicioId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Marca entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFecha(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCarga(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setEjercicioId(cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Marca entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Marca entity) {
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
    
    /** Internal query to resolve the "marcaList" to-many relationship of Ejercicio. */
    public List<Marca> _queryEjercicio_MarcaList(long ejercicioId) {
        synchronized (this) {
            if (ejercicio_MarcaListQuery == null) {
                QueryBuilder<Marca> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.EjercicioId.eq(null));
                ejercicio_MarcaListQuery = queryBuilder.build();
            }
        }
        Query<Marca> query = ejercicio_MarcaListQuery.forCurrentThread();
        query.setParameter(0, ejercicioId);
        return query.list();
    }

}
