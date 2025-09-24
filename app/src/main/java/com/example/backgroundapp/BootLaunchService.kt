package com.example.backgroundapp

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class BootLaunchService : Service() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Notification for foreground service
        val notification = Notification.Builder(this, "boot_channel")
            .setContentTitle("Boot Launch")
            .setContentText("Preparing app...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notification)

        // Launch MainActivity
        val launchIntent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(launchIntent)

        // Stop service after launching
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?) = null
}
