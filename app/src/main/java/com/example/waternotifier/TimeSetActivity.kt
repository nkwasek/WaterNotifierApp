package com.example.waternotifier

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TimeSetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_set)

        val okButton = findViewById<Button>(R.id.goalOkButton)
        okButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val startTime = findViewById<TextView>(R.id.startTime)

        val startTimeButton = findViewById<RelativeLayout>(R.id.startTimeButton)
        startTimeButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val textTime = hour.toString() + ":" + minute.toString().padStart(2, '0')
            startTime.setText(textTime)}
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        val stopTime = findViewById<TextView>(R.id.stopTime)

        val stopTimeButton = findViewById<RelativeLayout>(R.id.stopTimeButton)
        stopTimeButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                val textTime = hour.toString() + ":" + minute.toString().padStart(2, '0')
                stopTime.setText(textTime)}
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
    
}