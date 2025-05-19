package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littlelemon.habitzen.HabitManager.Habit
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


import com.littlelemon.habitzen.HabitManager.completedHabits
import com.littlelemon.habitzen.HabitManager.createdHabits
import java.text.SimpleDateFormat
import java.util.*

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)



        val clickableLayout = findViewById<LinearLayout>(R.id.clickableLayout)
        clickableLayout.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        val completedClick = findViewById<LinearLayout>(R.id.completedclc)
        completedClick.setOnClickListener {
            val intent = Intent(this, CompletedClick::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val daysLabel=findViewById<TextView>(R.id.daysLabel)
        val dayFilterSpinner = findViewById<Spinner>(R.id.dayFilterSpinner)
        val emptyMessageHome = findViewById<TextView>(R.id.emptyMessageHome)
        val statusMessage=findViewById<TextView>(R.id.todayStatusText)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get current day of the week
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val currentDay = dateFormat.format(Date())

        val completedHabitsSize=completedHabits.size
        val createdHabitsSize=createdHabits.size

        val todayCreatedHabits = getHabitsForToday()
        val todayCompletedHabits = getCompletedHabitsForToday()


        // ✅ Initialize text for accessibility
        emptyMessageHome.text = "No Habits Created Yet"
        //statusMessage.text="Today is ${currentDay} And You Have Worked On ${todayCreatedHabits.size} Out Of ${todayCompletedHabits.size} Habits That You Plan To Do Today"
        statusMessage.text = "Today is $currentDay. You have completed ${todayCompletedHabits.size} out of ${todayCreatedHabits.size} habits assigned for today."

        val dayOptions = arrayOf("All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayOptions) // Creates a connection between the list of days (dayOptions) and the drop-down (Spinner). ✔️ Uses a basic predefined layout (simple_spinner_item) to format how each item looks inside the Spinner.


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Makes sure the list looks neat when expanded (when the user taps the drop-down) and Uses another predefined layout (simple_spinner_dropdown_item) to format the drop-down options.



        dayFilterSpinner.adapter = adapter1

        val todayIndex = dayOptions.indexOf(currentDay)
        if (todayIndex != -1) {
            dayFilterSpinner.setSelection(todayIndex)
        }


        // ✅ Improved visibility logic
        if (HabitManager.createdHabits.isEmpty()) {
            daysLabel.visibility=View.GONE
            dayFilterSpinner.visibility = View.GONE // Hide drop-down
            emptyMessageHome.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            statusMessage.text="Today is ${currentDay}"
        } else {
            emptyMessageHome.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            dayFilterSpinner.visibility = View.VISIBLE // Show drop-down
            val adapter2 = HabitAdapter(HabitManager.createdHabits)
            recyclerView.adapter = adapter2
        }

        // pie chart logic starts


        val pieChart = findViewById<PieChart>(R.id.progressPieChart)

        if (createdHabits.isEmpty()) {
            pieChart.visibility = View.GONE
        } else {
            pieChart.visibility = View.VISIBLE
        }


        val totalHabits = todayCreatedHabits.size
        val completedCount = todayCompletedHabits.size
        val remainingCount = totalHabits - completedCount

        val entries = ArrayList<PieEntry>()
        if (completedCount > 0) entries.add(PieEntry(completedCount.toFloat(), "Completed"))
        if (remainingCount > 0) entries.add(PieEntry(remainingCount.toFloat(), "Remaining"))

        val dataSet = PieDataSet(entries, "Today's Progress")
        dataSet.setColors(
            resources.getColor(R.color.purple_200, theme), // Completed
            resources.getColor(R.color.light_green, theme) // Remaining
        )


        dataSet.valueTextSize = 18f
        dataSet.valueTextColor = resources.getColor(R.color.white, theme)

        val data = PieData(dataSet)
        pieChart.data = data

        // Customize legend and text styles
        val legend = pieChart.legend
        legend.textSize = 18f
        legend.textColor = resources.getColor(R.color.black, theme)
        legend.formSize = 20f


        pieChart.description.isEnabled = false
        pieChart.centerText = "Habit Progress"
        pieChart.setCenterTextSize(20f)
        pieChart.setEntryLabelColor(resources.getColor(android.R.color.black, theme))
        pieChart.animateY(1000)
        pieChart.invalidate() // Refresh char


        // piechart logic ends

        // ✅ Apply filtering logic when an item is selected
        dayFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDay = dayOptions[position]
                filterHabits(selectedDay) // Call function to filter habits
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
    }

    // ✅ Function to filter habits based on the selected day
    fun filterHabits(day: String) {

        val filteredHabits =
            if (day == "All") HabitManager.createdHabits else getHabitsForDay(day)
            val habitAdapter = HabitAdapter(filteredHabits)
            findViewById<RecyclerView>(R.id.recyclerView).adapter = habitAdapter
    }



    fun getHabitsForDay(day: String): List<Habit> {
        return HabitManager.createdHabits.filter { habit -> habit.days.contains(day) }
    }

    fun getHabitsForToday(): List<HabitManager.Habit> {
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val currentDay = dateFormat.format(Date()) // Get today's day dynamically
        return HabitManager.createdHabits.filter { habit -> habit.days.contains(currentDay) }
    }

    fun getCompletedHabitsForToday():List<CompletedHabit>
    {

        val todayHabits = getHabitsForToday().map { it.name } // Get names of today’s habits

        return HabitManager.completedHabits.filter { completedHabit -> todayHabits.contains(completedHabit.name)}


        }





}
