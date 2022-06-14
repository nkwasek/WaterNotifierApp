package com.example.waternotifier

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class TimeSetActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_set)

        val startTime = findViewById<TextView>(R.id.startTime)
        val stopTime = findViewById<TextView>(R.id.stopTime)
        val okButton = findViewById<Button>(R.id.timeSetOkButton)

        startTime.text = LocalVariables.DayStart
        stopTime.text = LocalVariables.DayEnd

        okButton.setOnClickListener{
            LocalVariables.DayStart = startTime.text.toString()
            LocalVariables.DayEnd = stopTime.text.toString()

            LocalVariables.calculateNotificationPeriod()
            LocalVariables.NotificationPeriodUserValue = LocalVariables.NotificationPeriod
            LocalVariables.scheduleNotification(this)

            writeData()
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val startTimeButton = findViewById<RelativeLayout>(R.id.startTimeButton)

        startTimeButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val textTime = hour.toString() + ":" + minute.toString().padStart(2, '0')
            startTime.setText(textTime)}
            TimePickerDialog(this, R.style.TimePickerTheme,  timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        val stopTimeButton = findViewById<RelativeLayout>(R.id.stopTimeButton)
        stopTimeButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                val textTime = hour.toString() + ":" + minute.toString().padStart(2, '0')
                stopTime.setText(textTime)}
            TimePickerDialog(this, R.style.TimePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    fun writeData() {
        val data = hashMapOf(
            "day_start" to LocalVariables.DayStart,
            "day_end" to LocalVariables.DayEnd,
            "goal" to LocalVariables.Goal,
            "progress" to LocalVariables.Progress,
        )

        db.collection(uid).document(LocalVariables.Today.toString())
            .set(data, SetOptions.merge())
    }

    override fun onBackPressed() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
    
}