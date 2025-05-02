package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val myButton = findViewById<Button>(R.id.LetsGetStarted)
        myButton.setOnClickListener {
            // Code to execute when the button is clicked
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
}