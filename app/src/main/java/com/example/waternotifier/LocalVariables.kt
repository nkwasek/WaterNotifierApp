package com.example.waternotifier
import android.content.Context
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


object LocalVariables {
    var Goal = 2000
    var Progress = 0
    var DayStart = "6:00"
    var DayEnd = "23:00"
    var YesterdayEnd = "23:00"

    var NotificationPeriod = 120

    var NotificationPeriodUserValue = -1
    var NotificationsEnabled = true

    var Today = Calendar.DAY_OF_WEEK
    var Yesterday = Today - 1

    val xAxis = mutableListOf<String>()
    val goals = mutableListOf<Float>()
    val progress = mutableListOf<Float>()


    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleNotification(context: Context) {

        cancelAlarm(context)

        if(NotificationsEnabled) {

            createNotificationChannel(context)

            val intent = Intent(context, Notification::class.java)
            intent.putExtra(notifyTitle, "Drink notification")
            intent.putExtra(notifyMessage, "Drink a glass of water!")

            pendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val startTime = now()
            var startTimeH = startTime.split(":")[0].toString().toInt()
            var startTimeM = startTime.split(":")[1].toString().toInt()


            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, startTimeH)
                set(Calendar.MINUTE, startTimeM)
            }

            val time = calendar.timeInMillis + NotificationPeriodUserValue * 60000

            Log.d(ContentValues.TAG, calendar.toString())
            Log.d(ContentValues.TAG, time.toString())
            Log.d(ContentValues.TAG, Calendar.MONTH.toString())

            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                time,
                (1000 * 60 * NotificationPeriodUserValue).toLong(),
                pendingIntent
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun cancelAlarm(context: Context) {

        if(this::alarmManager.isInitialized) {
            alarmManager?.cancel(pendingIntent)
        }
    }

    fun calculateNotificationPeriod() {
        var endH = Integer.parseInt(DayEnd.split(":")[0])
        var endM = Integer.parseInt(DayEnd.split(":")[1])
        var startH = Integer.parseInt(DayStart.split(":")[0])
        var startM = Integer.parseInt(DayStart.split(":")[1])

        var end = endH * 60 + endM
        var start = startH * 60 + startM

        var notificationsCount = Goal / Config.CUP_VOLUME

        if(end < start) {
            NotificationPeriod = (24 * 60 - start + end) / notificationsCount
        }
        else {
            NotificationPeriod = (end - start) / notificationsCount
        }
    }

    fun now(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

}