package com.littlelemon.habitzen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CreateNewHabit : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.createnewhabit)

        val homeclick = findViewById<LinearLayout>(R.id.home_Layout)
        val completed = findViewById<LinearLayout>(R.id.completed_new_habit)
        val createHabitButton = findViewById<Button>(R.id.createHabitButton)

        // 🏠 Navigate to Home
        homeclick.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        // ✅ Navigate to Completed Habits
        completed.setOnClickListener {
            val intent = Intent(this, CompletedClick::class.java)
            startActivity(intent)
        }

        // 🚀 Create Habit Button Click
        createHabitButton.setOnClickListener {
            validateAndCreateHabit()
        }

        // ✨ Button Press Animation (Scaling)
        createHabitButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                }
            }
            false
        }
    }

    private fun validateAndCreateHabit() {
        val habitNameField = findViewById<EditText>(R.id.habitNameInput)
        val habitName = habitNameField.text.toString().trim()
        val habitCategory = findViewById<Spinner>(R.id.spinner_one).selectedItem.toString()
        val selectedDays = getSelectedDays()

        if (habitName.isEmpty()) {
            showErrorDialog("Habit Name is required!")
            return
        }

        if (selectedDays.isEmpty()) {
            showErrorDialog("Please select at least one frequency!")
            return
        }

        createHabit(habitName, habitCategory, selectedDays)

        val intent = Intent(this, HomePage::class.java)
        intent.putExtra("habitName", habitName)
        startActivity(intent)
    }

    fun createHabit(habitName: String, habitCategory: String, selectedDays: String) {
        val habitNameValue = findViewById<EditText>(R.id.habitNameInput).text.toString()
        val habitCategoryValue = findViewById<Spinner>(R.id.spinner_one).selectedItem.toString()
        val selectedDaysValue = getSelectedDays()

        HabitManager.addHabit(HabitManager.Habit(habitNameValue, habitCategoryValue, selectedDaysValue))

        val intent = Intent(this, HomePage::class.java)
        intent.putExtra("habitName", habitNameValue)
        startActivity(intent)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error: Cannot Create Habit")
            .setMessage(message)
            .setPositiveButton("Try Again") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun getSelectedDays(): String {
        val selectedDaysList = mutableListOf<String>()
        if (findViewById<CheckBox>(R.id.checkBoxMonday).isChecked) selectedDaysList.add("Monday")
        if (findViewById<CheckBox>(R.id.checkBoxTuesday).isChecked) selectedDaysList.add("Tuesday")
        if (findViewById<CheckBox>(R.id.checkBoxWednesday).isChecked) selectedDaysList.add("Wednesday")
        if (findViewById<CheckBox>(R.id.checkBoxThursday).isChecked) selectedDaysList.add("Thursday")
        if (findViewById<CheckBox>(R.id.checkBoxFriday).isChecked) selectedDaysList.add("Friday")
        if (findViewById<CheckBox>(R.id.checkBoxSaturday).isChecked) selectedDaysList.add("Saturday")
        if (findViewById<CheckBox>(R.id.checkBoxSunday).isChecked) selectedDaysList.add("Sunday")
        return selectedDaysList.joinToString(", ")
    }
}
