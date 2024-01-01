package com.sample.arrive.ui.stationselect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.arrive.database.StationDatabaseRepository
import com.sample.arrive.database.StationInfo
import com.sample.arrive.network.HeartRailsExpressApi
import com.sample.arrive.network.Station
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class StationSelectViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: StationDatabaseRepository
) : ViewModel() {

    companion object {
        private val prefectureList = listOf(
            "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県",
            "福島県", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県",
            "東京都", "神奈川県", "新潟県", "富山県", "石川県", "福井県",
            "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県",
            "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県",
            "鳥取県", "島根県", "岡山県", "広島県", "山口県", "徳島県",
            "香川県", "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県",
            "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"
        )
        private const val defaultDistance = 1F
    }

    private val stationId: Long?

    init {
        stationId = savedStateHandle.get<Long>("StationId")
        if(stationId != null) {
            viewModelScope.launch {
                val stationInfo = repository.getStationInfo(stationId)
                    .filterNotNull()
                    .first()
                selectPrefecture(stationInfo.prefecture)
                selectLine(stationInfo.prefecture, stationInfo.line)
                selectStation(stationInfo.toStation())
                updateDistance(stationInfo.distance.toFloat())
            }
        }
    }

    val prefectureList = Companion.prefectureList

    var lineList: List<String> by mutableStateOf(emptyList())
        private set

    var stationList: List<Station> by mutableStateOf(emptyList())
        private set

    var selectedPrefecture: String? by mutableStateOf(null)
        private set

    var selectedLine: String? by mutableStateOf(null)
        private set

    var selectedStation: Station? by mutableStateOf(null)
        private set

    var distance: Float by mutableFloatStateOf(defaultDistance)
        private set

    enum class Action { NONE, COMMIT, CANCEL }
    var action: Action by mutableStateOf(Action.NONE)

    fun selectPrefecture(prefecture: String) {
        selectedPrefecture = prefecture
        updateLine(prefecture)
    }

    private fun updateLine(prefecture: String) {
        viewModelScope.launch {
            lineList = HeartRailsExpressApi.retrofitService
                .getLinesByPrefecture(prefecture).response.line
        }
        selectedLine = null
    }

    fun selectLine(prefecture: String, line: String) {
        selectedLine = line
        updateStation(prefecture, line)
    }

    private fun updateStation(prefecture: String, line: String) {
        viewModelScope.launch {
            stationList =  HeartRailsExpressApi.retrofitService
                .getStationsByLine(line, prefecture).response.station
        }
        selectedStation = null
    }

    fun selectStation(station: Station) {
        selectedStation = station
        distance = defaultDistance
    }

    fun updateDistance(value: Float) {
        distance = value
    }

    private fun toStationInfo(): StationInfo? {
        val prefecture = selectedPrefecture
        val line = selectedLine
        val station = selectedStation
        return if(prefecture == null || line == null || station == null) {
            null
        } else {
            StationInfo(
                stationId = stationId ?: 0,
                name = station.name,
                line = line,
                prefecture = prefecture,
                latitude = station.latitude,
                longitude = station.longitude,
                distance = distance.toDouble()
            )
        }
    }

    fun commit() {
        toStationInfo()?.let { stationInfo ->
            viewModelScope.launch {
                repository.insertStationInfo(stationInfo)
            }
        }
    }

    fun update() {
        toStationInfo()?.let { stationInfo ->
            viewModelScope.launch {
                repository.updateStationInfo(stationInfo)
            }
        }
    }
}
