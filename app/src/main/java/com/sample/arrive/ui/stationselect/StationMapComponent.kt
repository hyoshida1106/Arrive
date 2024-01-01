package com.sample.arrive.ui.stationselect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.sample.arrive.R
import com.sample.arrive.ui.component.GoogleMapComponent


@Composable
fun StationMapComponent(
    latitude: Double,
    longitude: Double,
    distance: Float,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = String.format("%.2f", distance))
            Slider(
                value = distance,
                onValueChange = onValueChanged,
                onValueChangeFinished = { },
                valueRange = 0.1f..2.0f
            )
        }

        GoogleMapComponent(
            latitude = latitude,
            longitude = longitude,
            zoom = 12.5f
        ) {
            Circle(
                center = LatLng(latitude, longitude),
                radius = distance * 1000.0,
                strokeWidth = 1F,
                strokeColor = Color(R.color.circle_stroke_color),
                fillColor = Color(R.color.circle_fill_color)
            )
        }
    }
}