package com.example.waternotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val goalButton = findViewById<Button>(R.id.goalChangeButton)
        goalButton.setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
        }
        val timeSetButton = findViewById<Button>(R.id.daytimeChangeButton)
        timeSetButton.setOnClickListener {
            val intent = Intent(this, TimeSetActivity::class.java)
            startActivity(intent)
        }
        val okButton = findViewById<Button>(R.id.goalOkButton)
        okButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}