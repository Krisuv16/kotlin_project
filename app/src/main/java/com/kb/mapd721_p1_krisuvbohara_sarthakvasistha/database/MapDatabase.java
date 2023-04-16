package com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MapEntity.class},version = 1)

public abstract  class MapDatabase extends RoomDatabase{

    public  abstract DaoMap mapDao();
    private static  volatile  MapDatabase INSTANCE;

    public static  MapDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (MapDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MapDatabase.class, "map_table").build();
            }
        }
        return INSTANCE;
    }
}