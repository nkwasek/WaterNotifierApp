package com.example.waternotifier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initGoalSpinners()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val addDrinkButton = findViewById<ImageButton>(R.id.addDrinkButton)
        addDrinkButton.setOnClickListener {
            val intent = Intent(this, AddDrinkActivity::class.java)
            startActivity(intent)
        }


    }


}