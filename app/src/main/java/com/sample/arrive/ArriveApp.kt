package com.sample.arrive

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.sample.arrive.database.AppContainer

/**
 * アプリケーションクラス
 */
class ArriveApp : Application()  {

    /**
     * アプリケーションコンテナインスタンスを保持する
     */
    val container = AppContainer(this)

    /**
     * インスタンス生成時の初期化処理
     */
    override fun onCreate() {
        super.onCreate()

//      DBを初期化するためのコード
//      val scope = CoroutineScope(Job() + Dispatchers.Main)
//      scope.launch {
//          container.repository.clearStationInfo()
//      }

        // 通知マネージャの参照を取得
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // ForegroundService用の通知チャネル
        val serviceChannel = NotificationChannel(ServiceChannelId,
            getString(R.string.arrive_service), NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(serviceChannel)

        // 設計通知用の通知チャネル
        val notifyChannel = NotificationChannel(NotifyChannelId,
            getString(R.string.arrival_notification), NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notifyChannel)
        notifyChannel.vibrationPattern = longArrayOf(0,500,500,500,500,500)
    }

    /**
     * アプリケーション終了処理
     */
    override fun onTerminate() {
        super.onTerminate()
        Log.i("Arrive", "OnTerminate")
    }

    companion object {
        const val ServiceChannelId = "ArriveService"
        const val NotifyChannelId  = "ArriveNotify"
    }
}

