package com.littlelemon.habitzen

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompleteHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_history) // ✅ Ensure this matches your XML filename

        val statsTextView = findViewById<TextView>(R.id.statsDisplay)
        val habitNameView = findViewById<TextView>(R.id.completedHabitName)

// ✅ Generate stats dynamically, ensuring proper spacing and readability
        val statsText = HabitManager.completedHabits.groupBy { it.assignedDay }.map { (day, habits) ->
            "$day | ${habits.size} habit(s)"
        }.joinToString("\n\n")

        val habitNamesText = HabitManager.completedHabits.map { "• ${it.name}" }.joinToString("\n")

        statsTextView.text = statsText // ✅ Updates the display with formatted text
        habitNameView.text = habitNamesText // ✅ Displays habit names dynamically





    }
}

