package com.example.waternotifier

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.DayOfWeek
import com.google.type.DayOfWeekProto
import java.text.SimpleDateFormat
import java.util.*


class DashboardActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid.toString()
    private var numberOfDocuments = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalVariables.Today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

        readData()

        Log.w(TAG, now())

        setContentView(R.layout.activity_dashboard)

        val progress = findViewById<TextView>(R.id.progressTextView)
        val achievement = findViewById<TextView>(R.id.achievementTextView)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val addDrinkButton = findViewById<ImageButton>(R.id.addDrinkButton)
        val signOutButton = findViewById<ImageButton>(R.id.signOutButton)
        val resetButton = findViewById<Button>(R.id.resetButton)


        if(LocalVariables.Progress < LocalVariables.Goal) {
            achievement.text = getString(R.string.goal_not_achieved)
        }
        else {
            achievement.text = getString(R.string.goal_achieved)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        addDrinkButton.setOnClickListener {
            val intent = Intent(this, AddDrinkActivity::class.java)
            startActivity(intent)
            finish()
        }

        signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        resetButton.setOnClickListener {
            LocalVariables.Progress = 0
            progress.text = LocalVariables.Progress.toString() + " ml / " + LocalVariables.Goal.toString() + " ml"
            writeData()
        }
    }

    fun writeData() {
        val data = hashMapOf(
            "day_start" to LocalVariables.DayStart,
            "day_end" to LocalVariables.DayEnd,
            "goal" to LocalVariables.Goal,
            "progress" to LocalVariables.Progress,
        )

        db.collection(uid).document(Calendar.DAY_OF_WEEK.toString())
            .set(data, SetOptions.merge())
    }


    fun readData() {

        db.collection(uid.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                val progress = findViewById<TextView>(R.id.progressTextView)

                numberOfDocuments = documents.count()

                if(numberOfDocuments > 0) {

                    for (document in documents) {
                        if (document.id == LocalVariables.Today.toString()) {
                            LocalVariables.DayStart = document.data.get("day_start").toString()
                        }
                        else if (document.id == LocalVariables.Yesterday.toString()) {
                            LocalVariables.YesterdayEnd = document.data.get("day_end").toString()
                        }
                    }

                    if (ifPreviousDay()) {
                        LocalVariables.Today = LocalVariables.Yesterday
                    }

                    for (document in documents) {
                        if (document.id == LocalVariables.Today.toString()) {
                            LocalVariables.DayStart = document.data.get("day_start").toString()
                            LocalVariables.DayEnd = document.data.get("day_end").toString()
                            LocalVariables.Progress = Integer.parseInt(document.data.get("progress").toString())
                            LocalVariables.Goal = Integer.parseInt(document.data.get("goal").toString())
                        }
                    }
                }
                progress.text = LocalVariables.Progress.toString() + " ml / " + LocalVariables.Goal.toString() + " ml"
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun now(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    private fun ifPreviousDay() : Boolean {
        var yesterdaYEndH = Integer.parseInt(LocalVariables.YesterdayEnd.split(":")[0])
        var yesterdaYEndM = Integer.parseInt(LocalVariables.YesterdayEnd.split(":")[1])
        var todayStartH = Integer.parseInt(LocalVariables.DayStart.split(":")[0])
        var todayStartM = Integer.parseInt(LocalVariables.DayStart.split(":")[1])
        var nowH = Integer.parseInt(now().split(":")[0])
        var nowM = Integer.parseInt(now().split(":")[1])

        if(yesterdaYEndH in 0 until todayStartH
            && nowH < yesterdaYEndH) {
            return true
        }
        else if(yesterdaYEndH in 0 until todayStartH
            && nowH == yesterdaYEndH && nowM < yesterdaYEndM) {
            return true
        }
        else if (yesterdaYEndH == todayStartH
            && yesterdaYEndM < todayStartM
            && nowH < yesterdaYEndH) {
            return true
        }
        else if (yesterdaYEndH == todayStartH
            && yesterdaYEndM < todayStartM
            && nowH == yesterdaYEndH && nowM < yesterdaYEndM) {
            return true
        }
        return false
    }
}


