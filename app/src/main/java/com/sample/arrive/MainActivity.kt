package com.sample.arrive

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import com.google.android.gms.maps.MapsInitializer
import com.sample.arrive.ui.location.LocationService
import com.sample.arrive.ui.main.MainNavigation
import com.sample.arrive.ui.theme.ArriveTheme

/**
 * アプリケーション唯一のアクティビティを定義する
 */
class MainActivity : ComponentActivity() {

    //実行に必要なPermission
    private val requiredPermissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.VIBRATE
    )

    /**
     * インスタンス初期化
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Arrive", "MainActivity::OnCreate")

        super.onCreate(savedInstanceState)

        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {
            Log.i("Arrive", "MapInitializer.Renderer:${it::javaClass.name}")
        }

        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { granted ->
            val refusedPermissions = granted.filter { !it.value }.keys
            if(refusedPermissions.isNotEmpty()) {
                setContent {
                    ArriveTheme {
                        Text("$refusedPermissions refused.")
                    }
                }
            } else {
                Intent(applicationContext, LocationService::class.java).apply {
                    val compo = startForegroundService(this)
                    Log.i("Arrive", "Start Server:${compo?.className}")
                }
                setContent {
                    ArriveTheme {
                        MainNavigation()
                    }
                }
            }
        }.launch(requiredPermissions)
    }

}