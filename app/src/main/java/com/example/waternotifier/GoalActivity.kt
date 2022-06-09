package com.example.waternotifier

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GoalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        initGoalSpinners()

        val okButton = findViewById<Button>(R.id.goalOkButton)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val minusButton = findViewById<ImageButton>(R.id.minusButton)
        val plusButton = findViewById<ImageButton>(R.id.plusButton)
        val dailyWater = findViewById<TextView>(R.id.dailyWater)

        var dailyValue = Integer.parseInt(dailyWater.text.split(' ')[0])

        okButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        minusButton.setOnClickListener{
            if (dailyValue<100) dailyValue=0 else dailyValue-=100
            seekBar.setProgress(dailyValue)
            dailyWater.text=dailyValue.toString() + " ml"
        }

        plusButton.setOnClickListener{
            if (dailyValue>4900) dailyValue=5000 else dailyValue+=100
            seekBar.setProgress(dailyValue)
            dailyWater.text=dailyValue.toString() + " ml"
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    dailyValue = progress
                    dailyWater.text = dailyValue.toString() + " ml"
                }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        }
        )
    }

    override fun onBackPressed() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
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
