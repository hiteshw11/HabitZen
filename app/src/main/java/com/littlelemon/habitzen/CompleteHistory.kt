package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CompleteHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complete_history) // ✅ Ensure correct layout is loaded

        val statsContainer = findViewById<LinearLayout>(R.id.statsContainer)

        // ✅ Check for null reference before accessing the container
        if (statsContainer != null) {
            // Group habits by assigned day
            val groupedHabits = HabitManager.completedHabits.groupBy { it.assignedDay }

            for ((day, habits) in groupedHabits) {
                // Create a horizontal layout for each day + its habits
                val dayRow = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(0, 16, 0, 10)
                }

                // Left column: Day label with habit count
                val dayTextView = TextView(this).apply {
                    text = "$day | ${habits.size} habit(s)"
                    textSize = 16f
                    setPadding(8, 0, 0, 0)
                    setTextColor(getColor(android.R.color.black))
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                // Right column: habits stacked vertically
                val habitListLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                habits.forEach { habit ->
                    val habitTextView = TextView(this).apply {
                        text = "• ${habit.name}"
                        textSize = 16f
                        setTextColor(getColor(android.R.color.darker_gray))
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
                        setPadding(0, 0, 8, 0)
                    }
                    habitListLayout.addView(habitTextView)
                }

                dayRow.addView(dayTextView)
                dayRow.addView(habitListLayout)
                statsContainer.addView(dayRow)
            }
        } else {
            // ✅ Error handling if `statsContainer` is not found
            println("Error: statsContainer not found in layout.")
        }

        // ✅ Bottom Navigation with Null Checks
        findViewById<LinearLayout>(R.id.home_Layout)?.setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        findViewById<LinearLayout>(R.id.create_new_completed)?.setOnClickListener {
            startActivity(Intent(this, CreateNewHabit::class.java))
        }

        findViewById<LinearLayout>(R.id.completed_new_habit)?.setOnClickListener {
            // Optional: refresh or keep on current
        }
    }
}