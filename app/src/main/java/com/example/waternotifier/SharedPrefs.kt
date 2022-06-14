package com.example.waternotifier

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs (context: Context) {
    var sharedpreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    fun readNotificationEnabled() {
        LocalVariables.NotificationsEnabled = sharedpreferences.getBoolean("notification", true)
    }

    fun readPeriod() {
        LocalVariables.NotificationPeriodUserValue = sharedpreferences.getInt("period", -1)
    }

    fun saveNotificationEnabled(value: Boolean) {
        sharedpreferences.edit().putBoolean("notification", value)
        sharedpreferences.edit().commit()
    }
    fun savePeriod() {
        sharedpreferences.edit().putInt("period", LocalVariables.NotificationPeriodUserValue)
        sharedpreferences.edit().commit()
    }
}