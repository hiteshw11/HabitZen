package com.littlelemon.habitzen



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//class HomePage : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.homepage)
//
//        val clickableLayout = findViewById<LinearLayout>(R.id.clickableLayout)
//        val completedClick  = findViewById<LinearLayout>(R.id.completedclc)
//
//        clickableLayout.setOnClickListener {
//            val intent = Intent(this, CreateNewHabit::class.java)
//            startActivity(intent)
//        }
//
//        completedClick.setOnClickListener {
//            val intent = Intent(this, CompletedClick::class.java)
//            startActivity(intent)
//        }
//
//        val habitName = intent.getStringExtra("habitName") ?: "Enter Your First Habit"
//        val habitCategory = intent.getStringExtra("habitCategory") ?: "Your First Habit Category"
//        val habitDays = intent.getStringExtra("habitDays") ?: " Your Habit Days"
//
//        findViewById<TextView>(R.id.habitNameDisplay).text = habitName
//        findViewById<TextView>(R.id.habitCategoryDisplay).text = habitCategory//"Your Habit Belongs To The "+habitCategory+" Category"
//        findViewById<TextView>(R.id.habitDaysDisplay).text = habitDays //"You Would Like To "+habitName+" On "+habitDays
//
//        val habitCheckBox=findViewById<CheckBox>(R.id.habitCheckBox)
//
//        habitCheckBox.setOnCheckedChangeListener { _,isChecked->
//            if(isChecked){
//                HabitManager.addCompletedHabit(habitName)
//            }
//
//            val intent = Intent(this,CompletedClick::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//}
class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        // ✅ Fix: Re-added Click Listener for "Create New" Button
        val clickableLayout = findViewById<LinearLayout>(R.id.clickableLayout)
        clickableLayout.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        // ✅ Fix: Added Click Listener for "Completed" Button
        val completedClick = findViewById<LinearLayout>(R.id.completedclc)
        completedClick.setOnClickListener {
            val intent = Intent(this, CompletedClick::class.java)
            startActivity(intent)
        }

        // ✅ RecyclerView Setup
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val emptyMessageHome = findViewById<TextView>(R.id.emptyMessageHome)
        recyclerView.layoutManager = LinearLayoutManager(this)




        if (HabitManager.createdHabits.isEmpty()) {
            emptyMessageHome.visibility = View.VISIBLE // Show "No created habits" text
        } else {
            emptyMessageHome.visibility = View.GONE // Hide message

        }
        val adapter = HabitAdapter(HabitManager.createdHabits) // ✅ Use HabitAdapter here
        recyclerView.adapter = adapter


    }

    }



