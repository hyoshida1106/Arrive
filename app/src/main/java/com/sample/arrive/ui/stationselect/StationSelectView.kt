package com.sample.arrive.ui.stationselect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sample.arrive.R
import com.sample.arrive.ui.component.DropDownList

@Composable
fun StationSelectView(
    model: StationSelectViewModel,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        DropDownList(
            label = stringResource(R.string.screen_select_prefecture),
            options = model.prefectureList,
            selectedOption = model.selectedPrefecture,
            onSelectionChanged = { index ->
                val prefecture = model.prefectureList[index]
                model.selectPrefecture(prefecture)
            }
        )

        val selectedPrefecture = model.selectedPrefecture
        if(selectedPrefecture != null) {
            DropDownList(
                label = stringResource(R.string.screen_select_line),
                options = model.lineList,
                selectedOption = model.selectedLine,
                onSelectionChanged = { index ->
                    val line = model.lineList[index]
                    model.selectLine(selectedPrefecture, line)
                }
            )

            val selectedLine = model.selectedLine
            if(selectedLine != null) {
                DropDownList(
                    label = stringResource(R.string.screen_select_station),
                    options = model.stationList.map { station -> station.name },
                    selectedOption = model.selectedStation?.name,
                    onSelectionChanged = { index ->
                        val station = model.stationList[index]
                        model.selectStation(station)
                    }
                )

                val selectedStation = model.selectedStation
                if(selectedStation != null) {
                    StationMapComponent(
                        latitude = selectedStation.latitude,
                        longitude = selectedStation.longitude,
                        distance = model.distance,
                        onValueChanged = { value -> model.updateDistance(value) }
                    )
                }
            }
        }
    }
}
