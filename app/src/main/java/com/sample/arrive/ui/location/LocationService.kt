package com.sample.arrive.ui.location

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.sample.arrive.ArriveApp
import com.sample.arrive.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn


class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val reportedIds = mutableListOf<Long>()

    private lateinit var locationClient: LocationClient

    companion object {
        var instance: LocationService? = null
        const val startNotificationId = 1
        fun approachNotificationId(stationId: Long): Int = stationId.toInt() + 2
    }

    init {
        instance = this
    }

    var location: Location? by mutableStateOf(null)
        private set

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.i("Arrive", "Service::onCreate")
        super.onCreate()
        locationClient = LocationClient(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val repo = (application as ArriveApp).container.repository
        combine(locationClient.getLocationUpdates(5000L), repo.getAllStationInfo()) { location, stationInfoList ->
            Log.i("Arrive", "Location(Server): $location")
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            this.location = location
            stationInfoList.forEach { stationInfo ->
                if(stationInfo.valid && stationInfo.withinDistance(location)) {
                    if(stationInfo.stationId !in reportedIds) {
                        Log.i("Arrive", "station: ${stationInfo.name}")
                        notificationManager.notify(
                            (approachNotificationId(stationInfo.stationId)),
                            buildApproachingNotification(stationInfo.name)
                        )
                        reportedIds.add(stationInfo.stationId)
                    }
                } else {
                    reportedIds.remove(stationInfo.stationId)
                    notificationManager.cancel(approachNotificationId(stationInfo.stationId))
                }
            }
        }.catch {
            e -> e.printStackTrace()
        }.launchIn(serviceScope)

        startForeground(startNotificationId, buildStartNotification())
        return super.onStartCommand(intent, flags, startId)
    }

    private fun buildStartNotification(): Notification {
        return NotificationCompat.Builder(this, ArriveApp.ServiceChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.service_channel_text))
            .setOngoing(true)
            .build()
    }

    private fun buildApproachingNotification(station: String): Notification {
        return NotificationCompat.Builder(this, ArriveApp.NotifyChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notify_channel_text, station))
            .setOngoing(false)
            .build()
    }

    private fun removeNotifications() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(startNotificationId)
        reportedIds.forEach { id ->
            notificationManager.cancel(approachNotificationId(id))
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.i("Arrive", "Service::onTaskRemoved")
        super.onTaskRemoved(rootIntent)
        removeNotifications()
        stopSelf()
        serviceScope.cancel()
    }
    override fun onDestroy() {
        Log.i("Arrive", "Service::onDestroy")
        super.onDestroy()
        removeNotifications()
        stopSelf()
        serviceScope.cancel()
    }

}