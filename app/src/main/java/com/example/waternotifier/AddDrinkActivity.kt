package com.example.waternotifier

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

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

//    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//        this.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(editable: Editable?) {
//                afterTextChanged.invoke(editable.toString())
//            }
//        })
//    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}