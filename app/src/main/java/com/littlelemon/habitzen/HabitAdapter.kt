package com.littlelemon.habitzen

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.littlelemon.habitzen.HabitManager.Habit

class HabitAdapter(private val habits: List<Habit>) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val habitName: TextView = itemView.findViewById(R.id.habitNameDisplay)
        val habitCategory: TextView = itemView.findViewById(R.id.habitCategoryDisplay)
        val habitCheckBox: CheckBox = itemView.findViewById(R.id.habitCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = habits[position]
        holder.habitName.text = habit.name
        holder.habitCategory.text = habit.category

        holder.habitCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                HabitManager.addCompletedHabit(habit.name)
                // ✅ Redirect to CompletedClick activity when checkbox is checked
                val intent = Intent(holder.itemView.context, CompletedClick::class.java)
                holder.itemView.context.startActivity(intent)

            }

        }
    }

    override fun getItemCount(): Int = habits.size
}