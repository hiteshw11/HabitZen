package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val clickableLayout = findViewById<LinearLayout>(R.id.clickableLayout)
        val completedClick  = findViewById<LinearLayout>(R.id.completedclc)

        clickableLayout.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        completedClick.setOnClickListener {
            val intent = Intent(this, CompletedClick::class.java)
            startActivity(intent)
        }

        val habitName = intent.getStringExtra("habitName") ?: "Enter Your First Habit"
        val habitCategory = intent.getStringExtra("habitCategory") ?: "Your First Habit Category"
        val habitDays = intent.getStringExtra("habitDays") ?: " Your Habit Days"

        findViewById<TextView>(R.id.habitNameDisplay).text = habitName
        findViewById<TextView>(R.id.habitCategoryDisplay).text = habitCategory//"Your Habit Belongs To The "+habitCategory+" Category"
        findViewById<TextView>(R.id.habitDaysDisplay).text = habitDays //"You Would Like To "+habitName+" On "+habitDays

        val habitCheckBox=findViewById<CheckBox>(R.id.habitCheckBox)

        habitCheckBox.setOnCheckedChangeListener { _,isChecked->
            if(isChecked){
                HabitManager.addCompletedHabit(habitName)
            }

            val intent = Intent(this,CompletedClick::class.java)
            startActivity(intent)
        }


    }
}
