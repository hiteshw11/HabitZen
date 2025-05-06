package com.littlelemon.habitzen

object HabitManager {
    val completedHabits = mutableListOf<String>()

    fun addCompletedHabit(habitName:String)
    {
        if(!completedHabits.contains(habitName))
        {
            completedHabits.add(habitName)
        }
    }
}