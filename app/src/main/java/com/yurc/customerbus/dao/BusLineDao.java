package com.yurc.customerbus.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.yurc.customerbus.dao.BusLine;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BUS_LINE.
*/
public class BusLineDao extends AbstractDao<BusLine, Long> {

    public static final String TABLENAME = "BUS_LINE";

    /**
     * Properties of entity BusLine.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property StartTime = new Property(1, String.class, "StartTime", false, "START_TIME");
        public final static Property EndTime = new Property(2, String.class, "EndTime", false, "END_TIME");
        public final static Property Linename = new Property(3, String.class, "linename", false, "LINENAME");
        public final static Property NearlySite = new Property(4, String.class, "nearlySite", false, "NEARLY_SITE");
        public final static Property Distance = new Property(5, String.class, "distance", false, "DISTANCE");
        public final static Property DirectionStie = new Property(6, String.class, "directionStie", false, "DIRECTION_STIE");
    };


    public BusLineDao(DaoConfig config) {
        super(config);
    }
    
    public BusLineDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BUS_LINE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'START_TIME' TEXT," + // 1: StartTime
                "'END_TIME' TEXT," + // 2: EndTime
                "'LINENAME' TEXT," + // 3: linename
                "'NEARLY_SITE' TEXT," + // 4: nearlySite
                "'DISTANCE' TEXT," + // 5: distance
                "'DIRECTION_STIE' TEXT);"); // 6: directionStie
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BUS_LINE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BusLine entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String StartTime = entity.getStartTime();
        if (StartTime != null) {
            stmt.bindString(2, StartTime);
        }
 
        String EndTime = entity.getEndTime();
        if (EndTime != null) {
            stmt.bindString(3, EndTime);
        }
 
        String linename = entity.getLinename();
        if (linename != null) {
            stmt.bindString(4, linename);
        }
 
        String nearlySite = entity.getNearlySite();
        if (nearlySite != null) {
            stmt.bindString(5, nearlySite);
        }
 
        String distance = entity.getDistance();
        if (distance != null) {
            stmt.bindString(6, distance);
        }
 
        String directionStie = entity.getDirectionStie();
        if (directionStie != null) {
            stmt.bindString(7, directionStie);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BusLine readEntity(Cursor cursor, int offset) {
        BusLine entity = new BusLine( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // StartTime
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // EndTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // linename
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // nearlySite
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // distance
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // directionStie
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BusLine entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStartTime(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEndTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLinename(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNearlySite(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDistance(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDirectionStie(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BusLine entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BusLine entity) {
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
