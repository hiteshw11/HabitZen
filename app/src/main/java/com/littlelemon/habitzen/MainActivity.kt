package com.littlelemon.habitzen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val myButton = findViewById<Button>(R.id.LetsGetStarted)
        myButton.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)

            // 🔄 Apply smooth custom animation using ActivityOptions
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right, // Enter animation for HomePage
                R.anim.slide_out_left  // Exit animation for MainActivity
            )

            startActivity(intent, options.toBundle())
        }
    }
}
