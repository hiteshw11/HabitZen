package com.littlelemon.habitzen

data class CompletedHabit(
    val name: String,
    val assignedDay: String,
    val category: String
)

object HabitManager {
    val completedHabits = mutableListOf<CompletedHabit>() // ✅ Store full habit objects

    fun addCompletedHabit(habitName: String, assignedDay: String, category: String) {
        val habit = CompletedHabit(habitName, assignedDay, category)
        completedHabits.add(habit)
    }

    fun removeCompletedHabit(habitName: String) {
        completedHabits.removeIf { it.name == habitName } // ✅ Removes habit from completed list
    }

    val createdHabits = mutableListOf<Habit>()

    fun addHabit(habit: Habit) {
        createdHabits.add(habit)
    }



    data class Habit(val name: String, val category: String, val days: String) // ✅ Stores all habit attributes
}