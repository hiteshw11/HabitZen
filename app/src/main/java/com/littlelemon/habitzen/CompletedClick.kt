package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedClick: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.completed_click)

        val create_new_button = findViewById<LinearLayout>(R.id.create_new_completed)

        val home_button = findViewById<LinearLayout>(R.id.home_Layout_completed)


        create_new_button.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        home_button.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }


//        val completedHabitsList = HabitManager.completedHabits.joinToString("\n") // Show all completed habits
//        findViewById<TextView>(R.id.completedHabitsDisplay).text = "Activity "+completedHabitsList+" Completed"


        val recyclerView = findViewById<RecyclerView>(R.id.completedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

// Show all completed habits
        val adapter = CompletedAdapter(HabitManager.completedHabits)
        recyclerView.adapter = adapter




    }
}