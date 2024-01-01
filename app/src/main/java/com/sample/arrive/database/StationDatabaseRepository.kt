package com.sample.arrive.database

import kotlinx.coroutines.flow.Flow


data class StationListState(val stationList: List<StationInfo> = emptyList())

class StationDatabaseRepository(
    private val stationDatabaseDao: StationDatabaseDao
) {
    fun getStationInfo(key: Long): Flow<StationInfo> = stationDatabaseDao.get(key)
    fun getAllStationInfo(): Flow<List<StationInfo>> = stationDatabaseDao.getAll()
    suspend fun insertStationInfo(stationInfo: StationInfo) = stationDatabaseDao.insert(stationInfo)
    suspend fun updateStationInfo(stationInfo: StationInfo) = stationDatabaseDao.update(stationInfo)
    suspend fun removeStationInfo(key: Long) = stationDatabaseDao.remove(key)
    suspend fun clearStationInfo() = stationDatabaseDao.clear()
}