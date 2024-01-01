package com.sample.arrive.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDatabaseDao {

    @Query("SELECT * FROM station_info_table WHERE stationId = :key")
    fun get(key: Long): Flow<StationInfo>

    @Query("SELECT * FROM station_info_table ORDER BY stationId DESC")
    fun getAll(): Flow<List<StationInfo>>

    @Insert
    suspend fun insert(stationInfo: StationInfo)

    @Update
    suspend fun update(stationInfo: StationInfo)

    @Query("DELETE FROM station_info_table WHERE stationId = :key")
    suspend fun remove(key: Long)

    @Query("DELETE FROM station_info_table")
    suspend fun clear()
}