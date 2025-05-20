package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.littlelemon.habitzen.HabitManager.Habit
import com.littlelemon.habitzen.HabitManager.completedHabits
import com.littlelemon.habitzen.HabitManager.createdHabits
import java.text.SimpleDateFormat
import java.util.*

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        // 🔹 Navigation Click Events
        findViewById<LinearLayout>(R.id.clickableLayout).setOnClickListener {
            startActivity(Intent(this, CreateNewHabit::class.java))
        }
        findViewById<LinearLayout>(R.id.completedclc).setOnClickListener {
            startActivity(Intent(this, CompletedClick::class.java))
        }

        // 🔹 UI Components
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val daysLabel = findViewById<TextView>(R.id.daysLabel)
        val dayFilterSpinner = findViewById<Spinner>(R.id.dayFilterSpinner)
        val emptyMessageHome = findViewById<TextView>(R.id.emptyMessageHome)
        val statusMessage = findViewById<TextView>(R.id.todayStatusText)
        val pieChart = findViewById<PieChart>(R.id.progressPieChart)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // 🔹 Get Current Day of the Week
        val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

        // 🔹 Track Habit Progress
        val todayCreatedHabits = getHabitsForToday()
        val todayCompletedHabits = getCompletedHabitsForToday()

        statusMessage.text = "Today is $currentDay. You have completed ${todayCompletedHabits.size} out of ${todayCreatedHabits.size} habits."

        // 🔹 Set Up Day Filter Spinner (Shifted Right)
        val dayOptions = arrayOf("All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayOptions)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dayFilterSpinner.adapter = adapter1

        val todayIndex = dayOptions.indexOf(currentDay)
        if (todayIndex != -1) dayFilterSpinner.setSelection(todayIndex)

        // 🔹 Visibility Logic
        if (createdHabits.isEmpty()) {
            daysLabel.visibility = View.GONE
            dayFilterSpinner.visibility = View.GONE
            emptyMessageHome.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            statusMessage.text = "Today is $currentDay"
            pieChart.visibility = View.GONE
        } else {
            emptyMessageHome.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            dayFilterSpinner.visibility = View.VISIBLE
            pieChart.visibility = View.VISIBLE
            recyclerView.adapter = HabitAdapter(createdHabits)
        }

        // 🔹 Setup PieChart (Progress Tracker)
        val totalHabits = todayCreatedHabits.size
        val completedCount = todayCompletedHabits.size
        val remainingCount = totalHabits - completedCount

        val entries = ArrayList<PieEntry>()
        if (completedCount > 0) entries.add(PieEntry(completedCount.toFloat(), "Completed"))
        if (remainingCount > 0) entries.add(PieEntry(remainingCount.toFloat(), "Remaining"))

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                resources.getColor(R.color.purple_200, theme), // Completed
                resources.getColor(R.color.light_green, theme) // Remaining
            )
            valueTextSize = 18f
            valueTextColor = resources.getColor(R.color.white, theme)
        }

        pieChart.data = PieData(dataSet)
        pieChart.description.isEnabled = false
        pieChart.centerText = "Habit Progress"
        pieChart.setCenterTextSize(22f) // ✅ Slightly larger
        pieChart.setEntryLabelColor(resources.getColor(android.R.color.black, theme))
        pieChart.animateY(1000)

        // 🔹 Center the "Today's Progress" text below the PieChart
        val legend = pieChart.legend
        legend.textSize = 20f  // ✅ Make text larger
        legend.textColor = resources.getColor(R.color.black, theme)
        legend.formSize = 20f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM  // ✅ Move legend below chart
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER  // ✅ Center it below PieChart
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)  // ✅ Ensure it's visible outside the chart

        pieChart.invalidate()

        // 🔹 Day Filter Logic
        dayFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterHabits(dayOptions[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    // 🔹 Function to Filter Habits Based on Selected Day
    private fun filterHabits(day: String) {
        val filteredHabits = if (day == "All") createdHabits else getHabitsForDay(day)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = HabitAdapter(filteredHabits)
    }

    private fun getHabitsForDay(day: String): List<Habit> {
        return createdHabits.filter { habit -> habit.days.contains(day) }
    }

    private fun getHabitsForToday(): List<Habit> {
        val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        return createdHabits.filter { habit -> habit.days.contains(currentDay) }
    }

    private fun getCompletedHabitsForToday(): List<CompletedHabit> {
        val todayHabits = getHabitsForToday().map { it.name }
        return completedHabits.filter { completedHabit -> todayHabits.contains(completedHabit.name) }
    }
}