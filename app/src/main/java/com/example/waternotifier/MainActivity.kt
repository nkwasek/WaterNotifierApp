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


    }

    fun initGoalSpinners()
    {
        val spinnerSex: Spinner = findViewById(R.id.spinner_sex)
        val spinnerLifestyle: Spinner = findViewById(R.id.spinner_lifestyle)

        ArrayAdapter.createFromResource(this, R.array.sex_array, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            spinnerSex.adapter = adapter
        }

        ArrayAdapter.createFromResource(this, R.array.lifestyle_array, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
            spinnerLifestyle.adapter = adapter
        }
    }
}