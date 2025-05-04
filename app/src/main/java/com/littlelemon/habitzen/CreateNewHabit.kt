package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateNewHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.createnewhabit) // to move to a different activity you need to val intent = Intent(this, CompletedHabits::class.java)
//            startActivity(intent), here the setContent view will just change layout of current page to a xml file (which is also needed) and not move to other activity
        val homeclick = findViewById<LinearLayout>(R.id.home_Layout)

        val completed = findViewById<LinearLayout>(R.id.completed_new_habit)
            homeclick.setOnClickListener {
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }

        completed.setOnClickListener {
            val intent = Intent(this, CompletedClick::class.java)
            startActivity(intent)
        }

    }
}