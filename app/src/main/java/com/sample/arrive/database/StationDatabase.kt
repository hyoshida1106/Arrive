package com.sample.arrive.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StationInfo::class], version = 1, exportSchema = false)
abstract class StationDatabase: RoomDatabase() {

    abstract val stationInfoDatabaseDao: StationDatabaseDao

    companion object {
        @Volatile
        private var instance_: StationDatabase? = null

        fun getInstance(context: Context): StationDatabase {
            return instance_ ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StationDatabase::class.java,
                    "station_info_table"
                ).fallbackToDestructiveMigration().build()
                instance_ = instance
                instance
            }
        }
    }
}