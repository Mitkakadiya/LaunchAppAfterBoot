package com.example.autostart

import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.backgroundapp.MainActivity

class BootService : Service() {

    private val userPresentReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, i: Intent) {
            if (Intent.ACTION_USER_PRESENT == i.action) {
                Log.d("BootService", "User unlocked, launching MainActivity")
                val launch = Intent(c, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(launch)
                stopSelf()
            }
        }
    }

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: Service Created")
        createNotificationChannel()

        val notification: Notification = NotificationCompat.Builder(this, "boot_channel")
            .setContentTitle("AutoStart Service")
            .setContentText("Waiting for user unlock…")
            .setSmallIcon(R.drawable.btn_star)
            .build()

        // Important: foreground service
        startForeground(1, notification)

        // Listen for the user unlocking the device
        registerReceiver(userPresentReceiver, IntentFilter(Intent.ACTION_USER_PRESENT))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(userPresentReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "boot_channel",
                "Boot Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }
}
