package com.example.waternotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        period.text = LocalVariables.NotificationPeriodUserValue.toString()

        goalValue.text = LocalVariables.Goal.toString() + " ml"
        dayStart.text = LocalVariables.DayStart
        dayStop.text = LocalVariables.DayEnd


        goalButton.setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
            finish()
        }

        val timeSetButton = findViewById<Button>(R.id.daytimeChangeButton)
        timeSetButton.setOnClickListener {
            val intent = Intent(this, TimeSetActivity::class.java)
            startActivity(intent)
            finish()
        }

        val okButton = findViewById<Button>(R.id.settingsOkButton)
        okButton.setOnClickListener{
            LocalVariables.NotificationPeriodUserValue = period.text.toString().toInt()
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