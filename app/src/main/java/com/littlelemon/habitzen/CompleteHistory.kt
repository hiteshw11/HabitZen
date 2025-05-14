package com.littlelemon.habitzen

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompleteHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_history) // ✅ Ensure this matches your XML filename

        val statsTextView = findViewById<TextView>(R.id.statsDisplay)

        // ✅ Create a map to store habits grouped by their assigned day
        val habitsByDay = HabitManager.completedHabits.groupBy { it.assignedDay }


        // ✅ Generate stats for each day
        val statsText = habitsByDay.map { (day, habits) ->
            "$day: ${habits.size} habits created\n${habits.joinToString(", ") { it.name }}"
        }.joinToString("\n\n") // ✅ Formats the output


        statsTextView.text = statsText // ✅ Display stats on the page



    }
}

