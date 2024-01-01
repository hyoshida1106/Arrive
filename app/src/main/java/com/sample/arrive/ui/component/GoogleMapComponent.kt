package com.sample.arrive.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapUiSettings

@Composable
fun GoogleMapComponent(
    latitude: Double,
    longitude: Double,
    zoom: Float,
    modifier: Modifier = Modifier,
    content: @Composable @GoogleMapComposable (() -> Unit)? = null
) {
    val cameraPositionState = CameraPositionState(
            CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), zoom))
    val uiSettings = MapUiSettings(
        compassEnabled = false,
        indoorLevelPickerEnabled = false,
        mapToolbarEnabled = false,
        myLocationButtonEnabled = false,
        rotationGesturesEnabled = false,
        scrollGesturesEnabled = false,
        scrollGesturesEnabledDuringRotateOrZoom = false,
        tiltGesturesEnabled = false,
        zoomControlsEnabled = false,
        zoomGesturesEnabled = false
    )
    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        modifier = modifier,
        content = content
    )
}