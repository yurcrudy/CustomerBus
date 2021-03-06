package com.yurc.customerbus.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.yurc.customerbus.dao.BusLineDetailDB;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BUS_LINE_DETAIL_DB.
*/
public class BusLineDetailDBDao extends AbstractDao<BusLineDetailDB, Long> {

    public static final String TABLENAME = "BUS_LINE_DETAIL_DB";

    /**
     * Properties of entity BusLineDetailDB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BusLineDetail = new Property(1, String.class, "BusLineDetail", false, "BUS_LINE_DETAIL");
        public final static Property City = new Property(2, String.class, "City", false, "CITY");
        public final static Property BusLineName = new Property(3, String.class, "BusLineName", false, "BUS_LINE_NAME");
    };


    public BusLineDetailDBDao(DaoConfig config) {
        super(config);
    }
    
    public BusLineDetailDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BUS_LINE_DETAIL_DB' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'BUS_LINE_DETAIL' TEXT," + // 1: BusLineDetail
                "'CITY' TEXT," + // 2: City
                "'BUS_LINE_NAME' TEXT UNIQUE );"); // 3: BusLineName
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BUS_LINE_DETAIL_DB'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BusLineDetailDB entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String BusLineDetail = entity.getBusLineDetail();
        if (BusLineDetail != null) {
            stmt.bindString(2, BusLineDetail);
        }
 
        String City = entity.getCity();
        if (City != null) {
            stmt.bindString(3, City);
        }
 
        String BusLineName = entity.getBusLineName();
        if (BusLineName != null) {
            stmt.bindString(4, BusLineName);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BusLineDetailDB readEntity(Cursor cursor, int offset) {
        BusLineDetailDB entity = new BusLineDetailDB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // BusLineDetail
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // City
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // BusLineName
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BusLineDetailDB entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBusLineDetail(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCity(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBusLineName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BusLineDetailDB entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BusLineDetailDB entity) {
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
