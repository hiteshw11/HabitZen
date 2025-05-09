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
        val dayFilterSpinner = findViewById<Spinner>(R.id.dayFilterSpinner)
        val emptyMessageHome = findViewById<TextView>(R.id.emptyMessageHome)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Initialize text for accessibility
        emptyMessageHome.text = "No Habits Created Yet"



        val dayOptions = arrayOf("All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayOptions) // Creates a connection between the list of days (dayOptions) and the drop-down (Spinner). ✔️ Uses a basic predefined layout (simple_spinner_item) to format how each item looks inside the Spinner.


        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Makes sure the list looks neat when expanded (when the user taps the drop-down) and Uses another predefined layout (simple_spinner_dropdown_item) to format the drop-down options.



        dayFilterSpinner.adapter = adapter1



        // ✅ Improved visibility logic
        if (HabitManager.createdHabits.isEmpty()) {
            dayFilterSpinner.visibility = View.GONE // Hide drop-down
            emptyMessageHome.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyMessageHome.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            dayFilterSpinner.visibility = View.VISIBLE // Show drop-down
            val adapter2 = HabitAdapter(HabitManager.createdHabits)
            recyclerView.adapter = adapter2
        }

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

        val filteredHabits = if (day == "All") {
            HabitManager.createdHabits // Show all habits
        }

        else
        {
            HabitManager.createdHabits.filter { habit -> day in habit.days }
        }

        val habitAdapter = HabitAdapter(filteredHabits)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = habitAdapter
    }





}
