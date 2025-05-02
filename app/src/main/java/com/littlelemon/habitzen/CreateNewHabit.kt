package com.littlelemon.habitzen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateNewHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.createnewhabit) // to move to a different activity you need to val intent = Intent(this, CompletedHabits::class.java)
//            startActivity(intent), here the setContent view will just change layout of current page and not move to other activity
    }
}