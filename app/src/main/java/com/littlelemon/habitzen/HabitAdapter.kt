package com.littlelemon.habitzen

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.littlelemon.habitzen.HabitManager.Habit

class HabitAdapter(
    private val habits: List<Habit>,
    private val onHabitCompleted: () -> Unit
) : RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

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
        holder.habitAssignedDay.visibility = View.GONE

        val isCompleted = HabitManager.completedHabits.any { it.name == habit.name }

        // Avoid re-triggering listener when recycling views
        holder.habitCheckBox.setOnCheckedChangeListener(null)
        holder.habitCheckBox.isChecked = isCompleted
        holder.habitName.paintFlags =
            if (isCompleted) holder.habitName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            else holder.habitName.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()

        holder.habitCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val activity = holder.itemView.context as Activity
            val habitCompletedText = activity.findViewById<TextView>(R.id.habitCompletedText) // ✅ Central screen text
            val tickAnimationView = activity.findViewById<LottieAnimationView>(R.id.completionAnimation) // ✅ Tick animation
            val allHabitsCompletedAnimation = activity.findViewById<LottieAnimationView>(R.id.congratulationsAnimation) // ✅ Celebration animation

            if (isChecked) {
                HabitManager.addCompletedHabit(habit.name, habit.days, habit.category)
                holder.habitName.paintFlags =
                    holder.habitName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

                // ✅ Play tick mark animation for this habit
                tickAnimationView.visibility = View.VISIBLE
                tickAnimationView.playAnimation()

                // ✅ Flashing animation for the central "Habit Completed" text
                habitCompletedText.visibility = View.VISIBLE
                habitCompletedText.alpha = 1f
                habitCompletedText.animate()
                    .alpha(0f).setDuration(700) // Fade out
                    .withEndAction {
                        habitCompletedText.animate()
                            .alpha(1f).setDuration(700) // Fade back in
                            .withEndAction {
                                habitCompletedText.animate()
                                    .alpha(0f).setDuration(700) // Second fade out
                                    .withEndAction {
                                        habitCompletedText.animate()
                                            .alpha(1f).setDuration(900) // Second fade in
                                            .withEndAction {
                                                habitCompletedText.visibility = View.GONE // Hide after flashing
                                                tickAnimationView.animate() // ✅ Fade out tick animation
                                                    .alpha(0f)
                                                    .setDuration(1500)
                                                    .withEndAction {
                                                        tickAnimationView.visibility = View.GONE
                                                        tickAnimationView.alpha = 1f // ✅ Reset for reuse
                                                    }
                                            }
                                    }
                            }
                    }

                // ✅ Trigger celebration animation when ALL habits are completed
                if (HabitManager.completedHabits.size == habits.size) {
                    allHabitsCompletedAnimation.visibility = View.VISIBLE
                    allHabitsCompletedAnimation.playAnimation()

                    allHabitsCompletedAnimation.postDelayed({
                        allHabitsCompletedAnimation.animate()
                            .alpha(0f)
                            .setDuration(2000)
                            .withEndAction {
                                allHabitsCompletedAnimation.visibility = View.GONE
                                allHabitsCompletedAnimation.alpha = 1f // ✅ Reset for future
                            }
                    }, 3000) // ✅ Celebration lasts for 3 seconds before fading
                }

            } else {
                HabitManager.removeCompletedHabit(habit.name) // ✅ Using function for clean removal
                holder.habitName.paintFlags = holder.habitName.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun getItemCount(): Int = habits.size
}