package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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

    private lateinit var congratulationsAnimation: LottieAnimationView
    private var hasShownCongrats = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        findViewById<LinearLayout>(R.id.clickableLayout).setOnClickListener {
            startActivity(Intent(this, CreateNewHabit::class.java))
        }

        findViewById<LinearLayout>(R.id.completedclc).setOnClickListener {
            startActivity(Intent(this, CompletedClick::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val daysLabel = findViewById<TextView>(R.id.daysLabel)
        val dayFilterSpinner = findViewById<Spinner>(R.id.dayFilterSpinner)
        val emptyMessageHome = findViewById<TextView>(R.id.emptyMessageHome)
        val statusMessage = findViewById<TextView>(R.id.todayStatusText)
        val pieChart = findViewById<PieChart>(R.id.progressPieChart)
        congratulationsAnimation = findViewById(R.id.congratulationsAnimation)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val dayOptions = arrayOf("All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayOptions)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dayFilterSpinner.adapter = adapter1

        val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        val todayIndex = dayOptions.indexOf(currentDay)
        if (todayIndex != -1) dayFilterSpinner.setSelection(todayIndex)

        val todayCreatedHabits = getHabitsForToday()
        val todayCompletedHabits = getCompletedHabitsForToday()

        updatePieChart(todayCreatedHabits.size, todayCompletedHabits.size)
        updateStatusText(todayCreatedHabits.size, todayCompletedHabits.size, currentDay)

        if (createdHabits.isEmpty()) {
            daysLabel.visibility = View.GONE
            dayFilterSpinner.visibility = View.GONE
            emptyMessageHome.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            pieChart.visibility = View.GONE
        } else {
            emptyMessageHome.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            dayFilterSpinner.visibility = View.VISIBLE
            pieChart.visibility = View.VISIBLE
            recyclerView.adapter = HabitAdapter(createdHabits.toMutableList()) { checkForAllHabitsCompleted() }
        }

        dayFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterHabits(dayOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterHabits(day: String) {
        val filteredHabits = if (day == "All") {
            createdHabits.toMutableList()
        } else {
            getHabitsForDay(day).toMutableList()
        }

        findViewById<RecyclerView>(R.id.recyclerView).adapter =
            HabitAdapter(filteredHabits) { checkForAllHabitsCompleted() }
    }

    private fun getHabitsForDay(day: String): List<Habit> {
        return createdHabits.filter { it.days.contains(day) }
    }

    private fun getHabitsForToday(): List<Habit> {
        val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        return createdHabits.filter { it.days.contains(currentDay) }
    }

    private fun getCompletedHabitsForToday(): List<CompletedHabit> {
        val todayHabitNames = getHabitsForToday().map { it.name }
        return completedHabits.filter { it.name in todayHabitNames }
    }

    private fun updateStatusText(total: Int, completed: Int, day: String) {
        val statusMessage = findViewById<TextView>(R.id.todayStatusText)
        statusMessage.text = if (total == 0) {
            "Today is $day"
        } else {
            "Today is $day. You have completed $completed out of $total habits."
        }
    }

    private fun updatePieChart(totalHabits: Int, completedCount: Int) {
        val pieChart = findViewById<PieChart>(R.id.progressPieChart)
        val remainingCount = totalHabits - completedCount

        val entries = ArrayList<PieEntry>().apply {
            if (completedCount > 0) add(PieEntry(completedCount.toFloat(), "Completed"))
            if (remainingCount > 0) add(PieEntry(remainingCount.toFloat(), "Remaining"))
        }

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                resources.getColor(R.color.purple_200, theme),
                resources.getColor(R.color.light_green, theme)
            )
            valueTextSize = 18f
            valueTextColor = resources.getColor(R.color.white, theme)
        }

        pieChart.data = PieData(dataSet)
        pieChart.description.isEnabled = false
        pieChart.centerText = "Habit Progress"
        pieChart.setCenterTextSize(22f)
        pieChart.setEntryLabelColor(resources.getColor(android.R.color.black, theme))
        pieChart.animateY(1000)

        val legend = pieChart.legend
        legend.textSize = 20f
        legend.textColor = resources.getColor(R.color.black, theme)
        legend.formSize = 20f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        pieChart.invalidate()
    }

    private fun checkForAllHabitsCompleted() {
        if (hasShownCongrats) return

        val todayCreated = getHabitsForToday().map { it.name }
        val todayCompleted = getCompletedHabitsForToday().map { it.name }

        if (todayCreated.isNotEmpty() && todayCreated.all { it in todayCompleted }) {
            hasShownCongrats = true
            showCongratulationsAnimation()
        }
    }

    private fun showCongratulationsAnimation() {
        congratulationsAnimation.visibility = View.VISIBLE
        congratulationsAnimation.playAnimation()

        congratulationsAnimation.postDelayed({
            congratulationsAnimation.animate()
                .alpha(0f)
                .setDuration(2000)
                .withEndAction {
                    congratulationsAnimation.visibility = View.GONE
                    congratulationsAnimation.alpha = 1f
                }
        }, 4000)
    }
}
