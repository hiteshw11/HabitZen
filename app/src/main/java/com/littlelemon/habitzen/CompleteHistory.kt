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
        val create_new_completed = findViewById<LinearLayout>(R.id.create_new_completed)
        create_new_completed.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        val homeclick = findViewById<LinearLayout>(R.id.home_Layout)

        homeclick.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

/// ✅ Generate a list where each habit appears on the right
        val habitNamesText = HabitManager.completedHabits.map { "• ${it.name}" }
            .joinToString("\n") // ✅ Ensures each habit is listed separately

        habitNameView.text = habitNamesText // ✅ Displays habits dynamically, right-aligned
        habitNameView.textAlignment = View.TEXT_ALIGNMENT_VIEW_END // ✅ Aligns text to the right

        // ✅ Generate stats dynamically, ensuring proper spacing and readability
        val statsText = HabitManager.completedHabits.groupBy { it.assignedDay }.map { (day, habits) -> "$day | ${habits.size} habit(s)" }.joinToString("\n\n")


        statsTextView.text = statsText // ✅ Updates the display with formatted text
        habitNameView.text = habitNamesText // ✅ Displays habit names dynamically





    }
}
