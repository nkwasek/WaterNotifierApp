package com.example.waternotifier

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import kotlin.math.round
import java.util.Collections.list
import kotlin.math.roundToInt
import kotlin.collections.List as List

class GoalActivity : AppCompatActivity() {
    var dailyValue = 0
//    private val okButton = findViewById<Button>(R.id.goalOkButton)
//    private val seekBar = findViewById<SeekBar>(R.id.seekBar)
//    private val minusButton = findViewById<ImageButton>(R.id.minusButton)
//    private val plusButton = findViewById<ImageButton>(R.id.plusButton)
//    private val dailyGoal = findViewById<TextView>(R.id.dailyGoal)
//    private val calculateButton = findViewById<Button>(R.id.calculateButton)
//
//    private val weightEditText = findViewById<EditText>(R.id.editWeight)
//    private val ageEditText = findViewById<EditText>(R.id.editAge)
//    private val sexSpinner = findViewById<Spinner>(R.id.spinnerSex)
//    private val lifestyleSpinner = findViewById<Spinner>(R.id.spinnerLifestyle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)
        initGoalSpinners()

        val okButton = findViewById<Button>(R.id.goalOkButton)

        val minusButton = findViewById<ImageButton>(R.id.minusButton)
        val plusButton = findViewById<ImageButton>(R.id.plusButton)
        val dailyGoal = findViewById<TextView>(R.id.dailyGoal)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        val weightEditText = findViewById<EditText>(R.id.editWeight)
        val ageEditText = findViewById<EditText>(R.id.editAge)
        val sexSpinner = findViewById<Spinner>(R.id.spinnerSex)
        val lifestyleSpinner = findViewById<Spinner>(R.id.spinnerLifestyle)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        dailyGoal.text = LocalVariables.Goal.toString()  + " ml";

        dailyValue = Integer.parseInt(dailyGoal.text.split(' ')[0])

        okButton.setOnClickListener{
            LocalVariables.Goal = Integer.parseInt(dailyGoal.text.split(' ')[0])
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        val text = "Empty values"
        val duration = Toast.LENGTH_SHORT
        calculateButton.setOnClickListener {
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

        minusButton.setOnClickListener{
            if (dailyValue<100) dailyValue=0 else dailyValue-=100
            seekBar.setProgress(dailyValue)
            dailyGoal.text = dailyValue.toString() + " ml"
        }

        plusButton.setOnClickListener{
            if (dailyValue>Config.MAX_VOLUME-100) dailyValue=Config.MAX_VOLUME else dailyValue+=100
            seekBar.setProgress(dailyValue)
            dailyGoal.text = dailyValue.toString() + " ml"
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    dailyValue = progress
                    dailyGoal.text = dailyValue.toString() + " ml"
                }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        }
        )


//        for (value in values) {
//            if (value == "")  notEmmpty = false
//        }

        weightEditText.doAfterTextChanged { afterTextChangedActions() }
        ageEditText.doAfterTextChanged { afterTextChangedActions() }

        sexSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                afterTextChangedActions()
            }
        }

        lifestyleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                afterTextChangedActions()
            }
        }

    }

    fun afterTextChangedActions() {
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val dailyGoal = findViewById<TextView>(R.id.dailyGoal)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        if (checkIfValuesNotEmpty()) {
            calculateButton.setOnClickListener {
                if (calculateGoal()>Config.MAX_VOLUME) dailyValue=Config.MAX_VOLUME else dailyValue=calculateGoal()
                seekBar.setProgress(dailyValue)
                dailyGoal.text = dailyValue.toString() + " ml"
            }
        }
        else {
            val text = "Empty values"
            val duration = Toast.LENGTH_SHORT
            calculateButton.setOnClickListener {
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }
    }

    fun checkIfValuesNotEmpty(): Boolean {
        val weight = findViewById<EditText>(R.id.editWeight).text.toString()
        val age = findViewById<EditText>(R.id.editAge).text.toString()
        val sex = findViewById<Spinner>(R.id.spinnerSex).selectedItem.toString()
        val lifestyle = findViewById<Spinner>(R.id.spinnerLifestyle).selectedItem.toString()

        val values: MutableList<String> = mutableListOf(weight, age, sex, lifestyle)

        var notEmpty = true

        for (value in values) {
            if (value == "")  notEmpty = false
        }
        return notEmpty
    }

    fun calculateGoal(): Int {
        val weight = Integer.parseInt(findViewById<EditText>(R.id.editWeight).text.toString())
        val age = Integer.parseInt(findViewById<EditText>(R.id.editAge).text.toString())
        val sex = findViewById<Spinner>(R.id.spinnerSex).selectedItem.toString()
        val lifestyle = findViewById<Spinner>(R.id.spinnerLifestyle).selectedItem.toString()

        // source: https://www.youtube.com/watch?v=IPyYDaTUGt8

        val weightInPounds = weight * 2.2046
        var goal = 0.0

        if (age <= 30) {
            goal = weightInPounds * 20 / 28.3 * 29.5735
        }
        else if (age in 31..55) {
            goal = weightInPounds * 17.5 / 28.3 * 29.5735
        }
        else {
            goal = weightInPounds * 15 / 28.3 * 29.5735
        }

        if(sex == "female") {
            goal *= Config.FEMALE_WATER_RATE
        }
        else if (sex == "male") {
            goal *= Config.MALE_WATER_RATE
        }

        if (lifestyle == "active") {
            goal += Config.ACTIVITY_BONUS
        }
        else if (lifestyle == "passive") {
            goal -= Config.PASSIVE_MINUS
        }

        return (goal/100).roundToInt() * 100
    }

    override fun onBackPressed() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun initGoalSpinners()
    {
        val spinnerSex: Spinner = findViewById(R.id.spinnerSex)
        val spinnerLifestyle: Spinner = findViewById(R.id.spinnerLifestyle)

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
