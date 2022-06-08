package com.example.waternotifier

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class AddDrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drink)

        val volumeValue = findViewById<EditText>(R.id.addDrinkEditTextVolumeValue)
        val waterPercentage = findViewById<EditText>(R.id.waterPercentage)

        val okButton = findViewById<Button>(R.id.addDrinkOkButton)
        okButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val cup = findViewById<RelativeLayout>(R.id.addDrinkCup)
        cup.setOnClickListener {
            volumeValue.setText("250")
        }

        val mug = findViewById<RelativeLayout>(R.id.addDrinkMug)
        mug.setOnClickListener {
            volumeValue.setText("350")
        }

        val bottle = findViewById<RelativeLayout>(R.id.addDrinkBottle)
        bottle.setOnClickListener {
            volumeValue.setText("500")
        }

        val otherVolume = findViewById<RelativeLayout>(R.id.addDrinkOtherVolume)
        otherVolume.setOnClickListener {
            volumeValue.setText("")
        }

        val water = findViewById<RelativeLayout>(R.id.addDrinkWater)
        water.setOnClickListener {
            waterPercentage.setText("100")
        }

        val juice = findViewById<RelativeLayout>(R.id.addDrinkJuice)
        juice.setOnClickListener {
            waterPercentage.setText("90")
        }

        val tea = findViewById<RelativeLayout>(R.id.addDrinkTea)
        tea.setOnClickListener {
            waterPercentage.setText("50")
        }

        val coffee = findViewById<RelativeLayout>(R.id.addDrinkCoffee)
        coffee.setOnClickListener {
            waterPercentage.setText("20")
        }

        val alcohol= findViewById<RelativeLayout>(R.id.addDrinkAlcohol)
        alcohol.setOnClickListener {
            waterPercentage.setText("0")
        }

        val otherDrink = findViewById<RelativeLayout>(R.id.addDrinkOtherDrink)
        otherDrink.setOnClickListener {
            waterPercentage.setText("")
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}