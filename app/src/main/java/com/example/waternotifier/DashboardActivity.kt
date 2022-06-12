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
import java.util.*


class DashboardActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readData()

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

                if(documents.count() > 0) {
                    LocalVariables.Progress = Integer.parseInt(documents.last().data.get("progress").toString())
                    LocalVariables.Goal = Integer.parseInt(documents.last().data.get("goal").toString())
                    progress.text = LocalVariables.Progress.toString() + " ml / " + LocalVariables.Goal.toString() + " ml"
                }
                else{
                    progress.text = LocalVariables.Progress.toString() + " ml / " + LocalVariables.Goal.toString() + " ml"
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}


