package com.example.backgroundapp

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.edit

class BootReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeIntentLaunch")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                Log.d("TAG", "onReceive: Background receiver received")

                context.let {
                    val prefs = context.getSharedPreferences("autolaunch_prefs", Context.MODE_PRIVATE)
                    prefs.edit { putBoolean("launched_after_boot", false) }
                }
//                android.os.Handler(context.mainLooper).postDelayed({
//                    val serviceIntent = Intent(context, BootLaunchService::class.java)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        context.startForegroundService(serviceIntent)
//                    } else {
//                        context.startService(serviceIntent)
//                    }
//                }, 10000)


//                val workRequest = OneTimeWorkRequestBuilder<BootWorker>()
//                    .setInitialDelay(5, TimeUnit.SECONDS) // optional delay
//                    .build()
//                WorkManager.getInstance(context).enqueue(workRequest)



//                val serviceIntent = Intent(context, BootService::class.java)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    context.startForegroundService(serviceIntent)
//                } else {
//                    context.startService(serviceIntent)
//                }
            }
        }
    }
}
