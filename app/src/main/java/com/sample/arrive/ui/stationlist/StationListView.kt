package com.sample.arrive.ui.stationlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sample.arrive.R
import com.sample.arrive.database.StationInfo
import com.sample.arrive.database.StationListState
import com.sample.arrive.ui.location.LocationService


@Composable
fun StationListView(
    model: StationListViewModel,
    onItemEdit: (StationInfo) -> Unit,
    onItemUpdate: (StationInfo) -> Unit,
    onItemRemove: (StationInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    val stationUiState by model.stationListStateFlow.collectAsState(initial = StationListState())
    var stationToRemove: StationInfo? by remember { mutableStateOf(null) }
    val service = LocationService.instance
    val location = service?.location

    Log.i("Arrive", "Location(ListView): $location")

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        // Show Station List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = stationUiState.stationList,
                key = { it.stationId }
            ) {stationInfo ->
                StationListItem(
                    stationInfo = stationInfo,
                    distance = if(location != null) stationInfo.getDistance(location) else null,
                    onItemEdit = onItemEdit,
                    onItemUpdate = onItemUpdate,
                    onItemRemove = { stationToRemove = it }
                )
            }
        }
    }

    // Alert to remove station
    if(stationToRemove != null) {
        AlertDialog(
            onDismissRequest = {
                stationToRemove = null
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onItemRemove(stationToRemove!!)
                        stationToRemove = null
                    }
                ) {
                    Text(stringResource(R.string.OK))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        stationToRemove = null
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = {
                Text("${stationToRemove!!.name} (${stationToRemove!!.line})")
            },
            text = {
                Text(stringResource(R.string.confirm_remove_station))
            },
        )
    }
}
