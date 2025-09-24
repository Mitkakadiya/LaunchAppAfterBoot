package com.example.backgroundapp

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AutoLaunchAccessibilityService : AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // You could trigger launch based on specific events
        // For example, when home screen is shown after boot:


        Log.d("AutoLaunch", "event: ${event.eventType}")
        // Try launching once when you detect a suitable event


        // Comment out this code to open the app every time or right after boot.
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            // Check if we already launched after boot
            val prefs = getSharedPreferences("autolaunch_prefs", Context.MODE_PRIVATE)
            val hasLaunched = prefs.getBoolean("launched_after_boot", false)
            if (!hasLaunched) {
                val launch = packageManager.getLaunchIntentForPackage("com.example.backgroundapp")
                launch?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launch)
                prefs.edit().putBoolean("launched_after_boot", true).apply()
            }
        }

//        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//            }//            val packageName =
////                if (event.getPackageName() != null) event.getPackageName().toString() else ""
////            if (packageName.contains("launcher")) {
////                // Home screen detected, launch app
////                launchApp()
//        }

    }

    override fun onInterrupt() {
        // Handle interruption
    }

}