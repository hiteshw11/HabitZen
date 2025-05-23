package com.littlelemon.habitzen

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
        val habitCompletedText: TextView = itemView.findViewById(R.id.habitCompletedText)
        val tickAnimationView: LottieAnimationView = itemView.findViewById(R.id.habitCompletionAnimation)
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

        holder.habitCheckBox.setOnCheckedChangeListener(null)
        holder.habitCheckBox.isChecked = isCompleted
        holder.habitName.paintFlags =
            if (isCompleted) holder.habitName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            else holder.habitName.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()

        holder.habitCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                HabitManager.addCompletedHabit(habit.name, habit.days, habit.category)
                holder.habitName.paintFlags =
                    holder.habitName.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

                holder.tickAnimationView.visibility = View.VISIBLE
                holder.tickAnimationView.playAnimation()

                holder.habitCompletedText.visibility = View.VISIBLE
                holder.habitCompletedText.alpha = 1f
                holder.habitCompletedText.animate()
                    .alpha(0f).setDuration(700)
                    .withEndAction {
                        holder.habitCompletedText.animate()
                            .alpha(1f).setDuration(700)
                            .withEndAction {
                                holder.habitCompletedText.animate()
                                    .alpha(0f).setDuration(700)
                                    .withEndAction {
                                        holder.habitCompletedText.animate()
                                            .alpha(1f).setDuration(900)
                                            .withEndAction {
                                                holder.habitCompletedText.visibility = View.GONE
                                                holder.tickAnimationView.animate()
                                                    .alpha(0f)
                                                    .setDuration(1500)
                                                    .withEndAction {
                                                        holder.tickAnimationView.visibility = View.GONE
                                                        holder.tickAnimationView.alpha = 1f
                                                        // ✅ Notify that a habit was completed after animation ends
                                                        onHabitCompleted()
                                                    }
                                            }
                                    }
                            }
                    }
            } else {
                HabitManager.removeCompletedHabit(habit.name)
                holder.habitName.paintFlags =
                    holder.habitName.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun getItemCount(): Int = habits.size
}
