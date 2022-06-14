package com.example.waternotifier

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val goalValue = findViewById<TextView>(R.id.goalSettingsTextview)
        val goalButton = findViewById<Button>(R.id.goalChangeButton)
        val dayStart = findViewById<TextView>(R.id.settingsDayStart)
        val dayStop = findViewById<TextView>(R.id.settingsDayEnd)
        val period = findViewById<TextView>(R.id.notificationPeriod)
        val switch = findViewById<SwitchCompat>(R.id.switch1)
        val timeSetButton = findViewById<Button>(R.id.daytimeChangeButton)

        period.text = LocalVariables.NotificationPeriodUserValue.toString()

        goalValue.text = LocalVariables.Goal.toString() + " ml"
        dayStart.text = LocalVariables.DayStart
        dayStop.text = LocalVariables.DayEnd

        switch.isChecked = LocalVariables.NotificationsEnabled

        if (switch.isChecked) {
            period.text = LocalVariables.NotificationPeriodUserValue.toString()
            period.isEnabled = true
            LocalVariables.NotificationsEnabled = true


        } else {
            period.text = ""
            period.isEnabled = false
            LocalVariables.NotificationsEnabled = false
        }

        goalButton.setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
            finish()
        }

        timeSetButton.setOnClickListener {
            val intent = Intent(this, TimeSetActivity::class.java)
            startActivity(intent)
            finish()
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                period.text = LocalVariables.NotificationPeriodUserValue.toString()
                period.isEnabled = true
                LocalVariables.NotificationsEnabled = true
            } else {
                period.text = ""
                period.isEnabled = false
                LocalVariables.NotificationsEnabled = false
            }
        }

        val okButton = findViewById<Button>(R.id.settingsOkButton)
        okButton.setOnClickListener{
            if(switch.isChecked) {
                LocalVariables.NotificationPeriodUserValue = period.text.toString().toInt()
            }

            LocalVariables.scheduleNotification(this)

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

}