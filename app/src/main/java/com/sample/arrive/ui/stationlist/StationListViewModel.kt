package com.sample.arrive.ui.stationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.arrive.database.StationDatabaseRepository
import com.sample.arrive.database.StationInfo
import com.sample.arrive.database.StationListState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class StationListViewModel(
    private val repository: StationDatabaseRepository
) : ViewModel() {

    val stationListStateFlow: StateFlow<StationListState> =
        repository.getAllStationInfo().map { list -> StationListState(list) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = StationListState()
            )

    fun update(stationInfo: StationInfo) {
        viewModelScope.launch {
            repository.updateStationInfo(stationInfo)
        }
    }

    fun remove(stationInfo: StationInfo) {
        viewModelScope.launch {
            repository.removeStationInfo(stationInfo.stationId)
        }
    }

}
