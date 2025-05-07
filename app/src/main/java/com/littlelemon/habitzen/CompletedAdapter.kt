package com.littlelemon.habitzen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompletedAdapter(private val completedHabits: List<String>) : RecyclerView.Adapter<CompletedAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val completedHabitName: TextView = itemView.findViewById(R.id.completedHabitName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_completed_habit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.completedHabitName.text = completedHabits[position]
    }

    override fun getItemCount(): Int = completedHabits.size
}