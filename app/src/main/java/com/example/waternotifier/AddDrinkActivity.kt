package com.example.waternotifier

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged

class AddDrinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_drink)

        val volumeValue = findViewById<EditText>(R.id.addDrinkEditTextVolumeValue)
        val waterPercentage = findViewById<EditText>(R.id.waterPercentage)
        val okButton = findViewById<Button>(R.id.addDrinkOkButton)

        val text = "Empty values!"
        val duration = Toast.LENGTH_SHORT
        okButton.setOnClickListener {
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

        volumeValue.doAfterTextChanged {
            if(volumeValue.text.toString() == "" || waterPercentage.text.toString() =="")
            {
                val text = "Empty values"
                val duration = Toast.LENGTH_SHORT
                okButton.setOnClickListener {
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
            }
            else {
                okButton.setOnClickListener {
                    val volume = volumeValue.text.toString().toDouble()
                    val hydration = waterPercentage.text.toString().toDouble()

                    val result = volume * hydration / 100

                    LocalVariables.Progress += result.toInt()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        waterPercentage.doAfterTextChanged {

            if(volumeValue.text.toString() == "" || waterPercentage.text.toString() =="") {
                val text = "Empty values"
                val duration = Toast.LENGTH_SHORT
                okButton.setOnClickListener {
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
            }
            else {
                val percentage = Integer.parseInt(waterPercentage.text.toString());
                if (percentage > 100) {
                    val text = "Water percentage out of range [0-100]"
                    val duration = Toast.LENGTH_SHORT
                    okButton.setOnClickListener {
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }
                }
                else {
                    okButton.setOnClickListener {
                        val volume = volumeValue.text.toString().toDouble()
                        val hydration = waterPercentage.text.toString().toDouble()

                        val result = volume * hydration / 100

                        LocalVariables.Progress += result.toInt()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }


        val cup = findViewById<RelativeLayout>(R.id.addDrinkCup)
        cup.setOnClickListener {
            volumeValue.setText(Config.CUP_VOLUME.toString())
        }

        val mug = findViewById<RelativeLayout>(R.id.addDrinkMug)
        mug.setOnClickListener {
            volumeValue.setText(Config.MUG_VOLUME.toString())
        }

        val bottle = findViewById<RelativeLayout>(R.id.addDrinkBottle)
        bottle.setOnClickListener {
            volumeValue.setText(Config.BOTTLE_VOLUME.toString())
        }

        val otherVolume = findViewById<RelativeLayout>(R.id.addDrinkOtherVolume)
        otherVolume.setOnClickListener {
            volumeValue.setText("")
        }

        val water = findViewById<RelativeLayout>(R.id.addDrinkWater)
        water.setOnClickListener {
            waterPercentage.setText(Config.WATER_PERCENTAGE.toString())
        }

        val juice = findViewById<RelativeLayout>(R.id.addDrinkJuice)
        juice.setOnClickListener {
            waterPercentage.setText(Config.JUICE_PERCENTAGE.toString())
        }

        val tea = findViewById<RelativeLayout>(R.id.addDrinkTea)
        tea.setOnClickListener {
            waterPercentage.setText(Config.TEA_PERCENTAGE.toString())
        }

        val coffee = findViewById<RelativeLayout>(R.id.addDrinkCoffee)
        coffee.setOnClickListener {
            waterPercentage.setText(Config.COFFEE_PERCENTAGE.toString())
        }

        val alcohol= findViewById<RelativeLayout>(R.id.addDrinkAlcohol)
        alcohol.setOnClickListener {
            waterPercentage.setText(Config.ALCOHOL_PERCENTAGE.toString())
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