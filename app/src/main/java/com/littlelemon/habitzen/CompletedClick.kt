package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedClick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.completed_click)

        val createNewButton = findViewById<LinearLayout>(R.id.create_new_completed)
        val homeButton = findViewById<LinearLayout>(R.id.home_Layout_completed)
        val completeHistory = findViewById<Button>(R.id.completeHistory)

        // ✨ Animate "View Habits Schedule" button on press
        completeHistory.setOnTouchListener { v, event ->
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

        // 🟣 Navigation
        createNewButton.setOnClickListener {
            startActivity(Intent(this, CreateNewHabit::class.java))
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, HomePage::class.java))
        }

        completeHistory.setOnClickListener {
            startActivity(Intent(this, CompleteHistory::class.java))
        }

        // 🧩 Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.completedRecyclerView)
        val emptyMessage = findViewById<TextView>(R.id.emptyMessage)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (HabitManager.completedHabits.isEmpty()) {
            emptyMessage.visibility = TextView.VISIBLE
        } else {
            emptyMessage.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE
            val completedAdapter = CompletedAdapter(HabitManager.completedHabits)
            recyclerView.adapter = completedAdapter
            completedAdapter.notifyDataSetChanged()
        }
    }
}
