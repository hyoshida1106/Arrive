package com.sample.arrive.ui.stationlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.sample.arrive.R
import com.sample.arrive.database.StationInfo
import com.sample.arrive.ui.component.GoogleMapComponent

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StationListItem(
    stationInfo : StationInfo,
    distance: Double?,
    onItemEdit: (StationInfo) -> Unit,
    onItemUpdate: (StationInfo) -> Unit,
    onItemRemove: (StationInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .requiredHeight(180.dp)
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    enabled = true,
                    onDoubleClick = { onItemEdit(stationInfo) },
                    onLongClick = { onItemEdit(stationInfo) },
                    onClick = { }
                )
        ){
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    leadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    supportingColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                leadingContent = {
                    Image(
                        painter = painterResource(id = R.drawable.transparent_subway_icon),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                },
                headlineContent = {
                    Text(text = stationInfo.name, fontWeight = FontWeight.Bold)
                },
                supportingContent = {
                    Text("${stationInfo.line} (${stationInfo.prefecture})")
                },
                trailingContent = @Composable {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        CompositionLocalProvider(
                            LocalMinimumInteractiveComponentEnforcement provides false
                        ) {
                            Row {
                                IconButton(
                                    onClick = {
                                        onItemUpdate(stationInfo.copy(valid = !stationInfo.valid))
                                    }
                                ) {
                                    val icon =
                                        if (stationInfo.valid) R.drawable.outline_check_box_24
                                        else R.drawable.outline_check_box_outline_blank_24
                                    Icon(
                                        painterResource(icon),
                                        stringResource(R.string.message_valid)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        onItemRemove(stationInfo)
                                    }
                                ) {
                                    val icon = R.drawable.outline_delete_forever_24
                                    Icon(
                                        painterResource(icon),
                                        stringResource(R.string.message_delete)
                                    )
                                }
                            }
                        }

                        val text = if(distance != null) {
                            stringResource(R.string.message_distance).format(distance)
                        } else {
                            "距離: ---"
                        }
                        val color = if (distance == null || !stationInfo.valid) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        } else if (distance <= stationInfo.distance) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                        Text(text = text, color = color)
                    }
                }
            )
            GoogleMapComponent(
                latitude = stationInfo.latitude,
                longitude = stationInfo.longitude,
                zoom = 12.5f,
                modifier = Modifier
                    .height(100.dp)
            ) {
                Marker(
                    state = MarkerState(LatLng(stationInfo.latitude, stationInfo.longitude)),
                    title = stationInfo.name
                )
            }
        }
    }
}
