package com.littlelemon.habitzen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val quoteTextView = findViewById<TextView>(R.id.quoteText)

        // 🔹 Motivational Quotes List
        val quotes = listOf(
            "Success is the sum of small efforts, repeated daily.",
            "Your only limit is your mind.",
            "A year from now, you'll wish you started today.",
            "Small habits lead to big changes!",
            "Consistency is more important than perfection.",
            "Believe in yourself and all that you are!",
            "Your future depends on what you do today!"
        )

        // 🔹 Display a Random Quote Every Time the App Opens
        quoteTextView.post {
            quoteTextView.text = quotes.random()
        }

        val myButton = findViewById<Button>(R.id.LetsGetStarted)
        myButton.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right, // Enter animation for HomePage
                R.anim.slide_out_left  // Exit animation for MainActivity
            )
            startActivity(intent, options.toBundle())
        }
    }
}