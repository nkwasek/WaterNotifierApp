package com.example.waternotifier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drink)
        //initGoalSpinners()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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