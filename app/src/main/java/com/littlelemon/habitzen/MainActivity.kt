package com.littlelemon.habitzen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 🔹 Load motivational quote
        val quoteTextView = findViewById<TextView>(R.id.quoteText)
        val quotes = listOf(
            "Success is the sum of small efforts, repeated daily.",
            "Your only limit is your mind.",
            "A year from now, you'll wish you started today.",
            "Small habits lead to big changes!",
            "Consistency is more important than perfection.",
            "Believe in yourself and all that you are!",
            "Your future depends on what you do today!"
        )
        quoteTextView.post {
            quoteTextView.text = quotes.random()
        }

        // 🔹 Setup button with animation and navigation
        val getStartedButton = findViewById<Button>(R.id.LetsGetStarted)

        // 👉 Navigate to HomePage with slide animation
        getStartedButton.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            startActivity(intent, options.toBundle())
        }

        // 👉 Add press scaling animation to button
        getStartedButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                }
            }
            false // allow click listener to still fire
        }
    }
}
