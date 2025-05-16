package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompleteHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_history) // ✅ Ensure this matches your XML filename

        val statsTextView = findViewById<TextView>(R.id.statsDisplay)
        val habitNameView = findViewById<TextView>(R.id.completedHabitName)

        val createNewCompleted = findViewById<LinearLayout>(R.id.create_new_completed)
        createNewCompleted.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        val homeClick = findViewById<LinearLayout>(R.id.home_Layout)
        homeClick.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        val groupedHabits = HabitManager.completedHabits.groupBy { it.assignedDay }
            .map { (day, habits) ->
                val habitList = habits.map { "• ${it.name}" }.joinToString("\n") // ✅ Ensures multiple habits appear below each other
                "$day | ${habits.size} habit(s)\n$habitList" // ✅ Displays habits aligned under their assigned day
            }.joinToString("\n\n")

        statsTextView.text = groupedHabits // ✅ Ensures correct habit grouping
        habitNameView.textAlignment = View.TEXT_ALIGNMENT_VIEW_END // ✅ Alig

        habitNameView.textAlignment = View.TEXT_ALIGNMENT_VIEW_END // ✅ Aligns habit names to the right
    }
}