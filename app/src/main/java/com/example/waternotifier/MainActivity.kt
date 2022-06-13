package com.example.waternotifier

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val uid = Firebase.auth.currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalVariables.Today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

        if (LocalVariables.Today == 1) {
            LocalVariables.Yesterday = 7;
        }

        setContentView(R.layout.activity_loading_screen)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val user = FirebaseAuth.getInstance().currentUser

        Handler().postDelayed({
            if (user != null) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)

    }

    fun readData() {
        db.collection(uid.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
                val progress = findViewById<TextView>(R.id.progressTextView)

                if(documents.count() > 0) {
                    LocalVariables.Progress = Integer.parseInt(documents.last().data.get("progress").toString())
                    LocalVariables.Goal = Integer.parseInt(documents.last().data.get("goal").toString())
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }


}