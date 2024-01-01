package com.sample.arrive.database

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.arrive.network.Station
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@Entity(tableName = "station_info_table")
data class StationInfo(
    @PrimaryKey(autoGenerate = true)
    var stationId: Long = 0L,

    @ColumnInfo(name = "station_name")
    var name: String,

    @ColumnInfo(name = "prefecture")
    var prefecture: String,

    @ColumnInfo(name = "line_name")
    var line: String,

    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.0,

    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0,

    @ColumnInfo(name = "distance")
    var distance: Double = 0.0,

    @ColumnInfo(name = "valid")
    var valid: Boolean = true
) {
    fun toStation(): Station {
        return Station(
            name = this.name,
            line = this.line,
            latitude = this.latitude,
            longitude = this.longitude
        )
    }

    fun withinDistance(location: Location): Boolean
        = getDistance(location) <= distance

    fun getDistance(location: Location): Double {
        fun Double.toRadian(): Double = this / 180.0 * Math.PI
        val lat1 = this.latitude.toRadian()
        val lon1 = this.longitude.toRadian()
        val lat2 = location.latitude.toRadian()
        val lon2 = location.longitude.toRadian()
        return acos((sin(lat1) * sin(lat2)) + (cos(lat1) * cos(lat2)) * (cos(lon1 - lon2))) * 6371
    }
}