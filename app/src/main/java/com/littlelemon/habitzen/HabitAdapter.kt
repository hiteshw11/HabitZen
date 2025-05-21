package com.littlelemon.habitzen

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.littlelemon.habitzen.HabitManager.Habit

class HabitAdapter(private val habits: List<Habit>) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val habitName: TextView = itemView.findViewById(R.id.habitNameDisplay)
        val habitCategory: TextView = itemView.findViewById(R.id.habitCategoryDisplay)
        val habitAssignedDay: TextView = itemView.findViewById(R.id.habitAssignedDayDisplay)
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
        holder.habitAssignedDay.visibility = View.GONE // ✅ Hide assigned day text

        // ✅ Apply strikethrough if habit is completed
        if (HabitManager.completedHabits.any { it.name == habit.name }) {
            holder.habitName.paintFlags = holder.habitName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            holder.habitCheckBox.isChecked = true
        } else {
            holder.habitName.paintFlags = holder.habitName.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.habitCheckBox.isChecked = false
        }

        // ✅ Trigger animation when checkbox is checked
        holder.habitCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                HabitManager.addCompletedHabit(habit.name, habit.days, habit.category)

                val activity = holder.itemView.context as Activity
                val animationView = activity.findViewById<LottieAnimationView>(R.id.completionAnimation)
                animationView.visibility = View.VISIBLE
                animationView.playAnimation()

                // ✅ Ensure transition only happens after animation completes
                animationView.addAnimatorUpdateListener { animator ->
                    if (animator.animatedFraction == 1f) { // ✅ Animation reaches full completion
                        animationView.visibility = View.GONE
                        val intent = Intent(holder.itemView.context, CompletedClick::class.java)
                        holder.itemView.context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = habits.size
}