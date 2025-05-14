package com.littlelemon.habitzen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedClick: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.completed_click)

        val createNewButton = findViewById<LinearLayout>(R.id.create_new_completed)
        val homeButton = findViewById<LinearLayout>(R.id.home_Layout_completed)
        val completeHistory=findViewById<Button>(R.id.completeHistory)

        createNewButton.setOnClickListener {
            val intent = Intent(this, CreateNewHabit::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        completeHistory.setOnClickListener {
            val intent=Intent(this, CompleteHistory::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.completedRecyclerView)
        val emptyMessage = findViewById<TextView>(R.id.emptyMessage)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Check if there are completed habits before setting the adapter
        if (HabitManager.completedHabits.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE // Show "No completed habits" text
        } else {
            emptyMessage.visibility = View.GONE // Hide message
            recyclerView.visibility = View.VISIBLE

            val completedAdapter = CompletedAdapter(HabitManager.completedHabits)
            recyclerView.adapter = completedAdapter
            completedAdapter.notifyDataSetChanged() // ✅ Ensure RecyclerView refreshes dynami

        }
    }
}