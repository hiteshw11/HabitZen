package com.littlelemon.habitzen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.littlelemon.habitzen.HabitManager.Habit

class CreateNewHabit : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.createnewhabit) // to move to a different activity you need to val intent = Intent(this, CompletedHabits::class.java)
//            startActivity(intent), here the setContent view will just change layout of current page to a xml file (which is also needed) and not move to other activity
        val homeclick = findViewById<LinearLayout>(R.id.home_Layout)

        val completed = findViewById<LinearLayout>(R.id.completed_new_habit)

            homeclick.setOnClickListener {
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }

        completed.setOnClickListener {
            val intent = Intent(this, CompletedClick::class.java)
            startActivity(intent)
        }

        val createHabitButton = findViewById<Button>(R.id.createHabitButton)
        createHabitButton.setOnClickListener { createHabit() }
    }



    fun createHabit() {
        val habitName = findViewById<EditText>(R.id.habitNameInput).text.toString()
        val habitCategory = findViewById<Spinner>(R.id.spinner_one).selectedItem.toString()
        val selectedDays = getSelectedDays()


        HabitManager.addHabit(Habit(habitName,habitCategory,selectedDays))

        intent.putExtra("habitName", habitName)
        // Pass data to HomePage using Intent Extras
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

    }

//
//        intent.putExtra("habitCategory", habitCategory)
//        intent.putExtra("habitDays", selectedDays)
//        startActivity(intent)
//
//    }
//


    fun getSelectedDays():String
    {
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




