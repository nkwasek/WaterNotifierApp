package com.example.waternotifier

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class DashboardActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid.toString()
    private var numberOfDocuments = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalVariables.Today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

        readData()

        //Log.w(TAG, Config.WEEK_DAYS.toString())

        setContentView(R.layout.activity_dashboard)

        val progress = findViewById<TextView>(R.id.progressTextView)
        val achievement = findViewById<TextView>(R.id.achievementTextView)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val addDrinkButton = findViewById<ImageButton>(R.id.addDrinkButton)
        val signOutButton = findViewById<ImageButton>(R.id.signOutButton)
        val resetButton = findViewById<Button>(R.id.resetButton)


        if(LocalVariables.Progress < LocalVariables.Goal) {
            achievement.text = getString(R.string.goal_not_achieved)
        }
        else {
            achievement.text = getString(R.string.goal_achieved)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        addDrinkButton.setOnClickListener {
            val intent = Intent(this, AddDrinkActivity::class.java)
            startActivity(intent)
            finish()
        }

        signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetButton.setOnClickListener {
            LocalVariables.Progress = 0
            progress.text = LocalVariables.Progress.toString() + " ml / " + LocalVariables.Goal.toString() + " ml"
            writeData()
            this.recreate()
        }
    }

    fun writeData() {
        val data = hashMapOf(
            "day_start" to LocalVariables.DayStart,
            "day_end" to LocalVariables.DayEnd,
            "goal" to LocalVariables.Goal,
            "progress" to LocalVariables.Progress,
            "period" to LocalVariables.NotificationPeriodUserValue,
            "if_notification" to LocalVariables.NotificationsEnabled,
        )

        db.collection(uid).document(LocalVariables.Today.toString())
            .set(data, SetOptions.merge())
    }


    fun readData() {
        LocalVariables.xAxis.clear()
        LocalVariables.goals.clear()
        LocalVariables.progress.clear()
        Log.w(TAG, "READ DATA")

        db.collection(uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                val progress = findViewById<TextView>(R.id.progressTextView)
                Log.w(TAG, "LISTENER")

                if(documents.count() > 0) {

                    for (document in documents) {
                        if (document.id == LocalVariables.Today.toString()) {
                            LocalVariables.DayStart = document.data.get("day_start").toString()
                        }
                        else if (document.id == LocalVariables.Yesterday.toString()) {
                            LocalVariables.YesterdayEnd = document.data.get("day_end").toString()
                        }
                    }

                    if (ifPreviousDay()) {
                        LocalVariables.Today = LocalVariables.Yesterday
                    }
                    fillXAxis()

                    for (document in documents) {

                        if (document.id == LocalVariables.Today.toString()) {
                            LocalVariables.DayStart = document.data.get("day_start").toString()
                            LocalVariables.DayEnd = document.data.get("day_end").toString()
                            LocalVariables.Progress = Integer.parseInt(document.data.get("progress").toString())
                            LocalVariables.Goal = Integer.parseInt(document.data.get("goal").toString())
                            LocalVariables.NotificationsEnabled = document.data.get("if_notification").toString().toBoolean()
                            LocalVariables.NotificationPeriodUserValue = Integer.parseInt(document.data.get("period").toString())
                        }
                    }

                    var start = LocalVariables.Today
                    if(LocalVariables.Today == 1) start = 1

                    for (i in 0..6) {
                        var ifFound = false
                        for (document in documents) {
                            if (document.id.toInt() == (7 + start + i) % 7 + 1) {
                                LocalVariables.progress.add(i, document.data.get("progress").toString().toFloat())
                                LocalVariables.goals.add(i, document.data.get("goal").toString().toFloat())
                                Log.w(TAG, "ADD")
                                ifFound = true
                                break
                            }
                        }
                        if (!ifFound) {
                            LocalVariables.progress.add(i, 0f)
                            LocalVariables.goals.add(i, 0f)
                            Log.w(TAG, "ADD ZERO " + i.toString())
                        }
                        
                    }
                }
                progress.text = LocalVariables.Progress.toString() + " ml / " + LocalVariables.Goal.toString() + " ml"
                plotChart()

                if (LocalVariables.NotificationPeriodUserValue == -1) {
                    LocalVariables.NotificationPeriodUserValue = LocalVariables.NotificationPeriod
                }

                LocalVariables.calculateNotificationPeriod()
                LocalVariables.scheduleNotification(this)

                Log.w(TAG, LocalVariables.xAxis.toString())
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun plotChart() {

        setBarChart(LocalVariables.progress as ArrayList<Float>, LocalVariables.xAxis as ArrayList<String>)

    }

    private fun setBarChart(values: ArrayList<Float>, xAxisLabels: ArrayList<String>) {

        val barChart = findViewById<BarChart>(R.id.barChart)

        val entries = ArrayList<BarEntry>()
        entries.clear()
        val colors = ArrayList<Int>()
        colors.clear()

        var it = 0f
        var sum = 0f

        for(i in 0..values.size-1) {
            entries.add(BarEntry(it, values[i]))
            it++
            sum += values[i]
            if(values[i] >= LocalVariables.goals[i]) {
                colors.add(resources.getColor(R.color.green))
            }
            else {
                colors.add(resources.getColor(R.color.red))
            }
        }

//        values.forEach { value ->
//            entries.add(BarEntry(it, value))
//            colors.add(resources.getColor(R.color.text_color))
//            it++
//        }

        val barDataSet = BarDataSet(entries, "Sum from last 7 day: " + (sum/1000) + "l")
        barDataSet.valueTextSize = 14f
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.legend.isEnabled = false
        barChart.xAxis.textSize = 12f
        barChart.axisLeft.textSize = 12f
        barDataSet.colors = colors
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setVisibleXRangeMaximum(7f)
        barChart.xAxis.labelCount = values.size
        barChart.xAxis.xOffset = 15f
        barChart.xAxis.granularity = 1f
        barChart.xAxis.setDrawLabels(true)
        barChart.xAxis.spaceMin = 5f
        barChart.xAxis.spaceMax = 25f
        barChart.moveViewToX((values.size / 2).toFloat())
        barChart.setPinchZoom(true)
        barChart.invalidate()
        barChart.animateY(2000)

    }



    fun now(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    private fun ifPreviousDay() : Boolean {
        var yesterdayEndH = Integer.parseInt(LocalVariables.YesterdayEnd.split(":")[0])
        var yesterdayEndM = Integer.parseInt(LocalVariables.YesterdayEnd.split(":")[1])
        var todayStartH = Integer.parseInt(LocalVariables.DayStart.split(":")[0])
        var todayStartM = Integer.parseInt(LocalVariables.DayStart.split(":")[1])
        var nowH = Integer.parseInt(now().split(":")[0])
        var nowM = Integer.parseInt(now().split(":")[1])

        if(yesterdayEndH in 0 until todayStartH
            && nowH < yesterdayEndH) {
            return true
        }
        else if(yesterdayEndH in 0 until todayStartH
            && nowH == yesterdayEndH && nowM < yesterdayEndM) {
            return true
        }
        else if (yesterdayEndH == todayStartH
            && yesterdayEndM < todayStartM
            && nowH < yesterdayEndH) {
            return true
        }
        else if (yesterdayEndH == todayStartH
            && yesterdayEndM < todayStartM
            && nowH == yesterdayEndH && nowM < yesterdayEndM) {
            return true
        }
        return false
    }

    fun fillXAxis() {

        var start = LocalVariables.Today
        if(LocalVariables.Today == 1) start = 1

        for (i in 0..6) {
            LocalVariables.xAxis.add(Config.WEEK_DAYS[(7 + start + i) % 7])
            Log.w(TAG, "ADD AXIS " + i.toString())
            //LocalVariables.progress.add(0f)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        intent.putExtra(notifyTitle, "Drink notification")
        intent.putExtra(notifyMessage, "Drink a glass of water!")
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance()
        calendar.set(2022,5,14,22,4)
        val time = calendar.timeInMillis
        Log.d(TAG, calendar.toString())
        Log.d(TAG, time.toString())
        Log.d(TAG, Calendar.MONTH.toString())
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}


