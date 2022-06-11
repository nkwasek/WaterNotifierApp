package com.example.waternotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val goalValue = findViewById<TextView>(R.id.goalSettingsTextview)
        val goalButton = findViewById<Button>(R.id.goalChangeButton)
        val dayStart = findViewById<TextView>(R.id.settingsDayStart)
        val dayStop = findViewById<TextView>(R.id.settingsDayEnd)

        goalValue.text = LocalVariables.Goal.toString() + " ml"
        dayStart.text = LocalVariables.DayStart.toString()
        dayStop.text = LocalVariables.DayEnd.toString()


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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}