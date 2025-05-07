package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
        val emptyMessageHome = findViewById<TextView>(R.id.emptyMessageHome)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Initialize text for accessibility
        emptyMessageHome.text = "No Habits Created Yet"

        // ✅ Improved empty state logic
        if (HabitManager.createdHabits.isEmpty()) {
            emptyMessageHome.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyMessageHome.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            val adapter = HabitAdapter(HabitManager.createdHabits)
            recyclerView.adapter = adapter
        }
    }
}