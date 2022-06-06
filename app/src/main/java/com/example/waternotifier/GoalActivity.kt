package com.example.waternotifier

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class GoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        initGoalSpinners()

        val okButton = findViewById<Button>(R.id.goalOkButton)
        okButton.setOnClickListener{
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
