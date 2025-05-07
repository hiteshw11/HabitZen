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


    val createdHabits= mutableListOf<Habit>()

    fun addHabit(habit:Habit)
    {
        createdHabits.add(habit)
    }

    data class Habit(val name:String, val category:String,val days:String) // so we can store all attributes of habit such as name,category and days of each habit in a single object






}