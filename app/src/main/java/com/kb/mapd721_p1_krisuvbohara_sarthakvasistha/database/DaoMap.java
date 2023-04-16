package com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoMap {
    @Insert
    void insert(MapEntity mapEntity);

    @Query("SELECT * FROM map_entity")
    List<MapEntity> getAll();
}
