package com.example.waternotifier

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val channelID = "channel1"
const val notifyTitle = "Drink notification"
const val notifyMessage = "Drink a glass of water!"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(notifyTitle))
            .setContentText(intent.getStringExtra(notifyMessage))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

}