package com.littlelemon.habitzen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompletedAdapter(private val completedHabits: List<CompletedHabit>) : RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val completedHabitName: TextView = itemView.findViewById(R.id.completedHabitName)
        val completedHabitCategory: TextView = itemView.findViewById(R.id.completedHabitCategory)
        val completedHabitDay: TextView = itemView.findViewById(R.id.completedHabitDay)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_completed_habit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = completedHabits[position]
        holder.completedHabitName.text = habit.name
        holder.completedHabitCategory.text = "Category: ${habit.category}"
        holder.completedHabitDay.text = "                               Assigned Day: ${habit.assignedDay}" // ✅ Dis

    }

    override fun getItemCount(): Int = completedHabits.size
}