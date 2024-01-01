package com.sample.arrive.database

import android.content.Context

class AppContainer(private val context: Context) {
    val repository: StationDatabaseRepository by lazy {
        StationDatabaseRepository(
            StationDatabase.getInstance(context).stationInfoDatabaseDao)
    }
}