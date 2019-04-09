package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.epamtraining.R
import com.example.epamtraining.models.Exercises
import com.example.epamtraining.models.Food
import com.example.epamtraining.models.Trainings
import com.example.epamtraining.models.Users
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign_in_button.setOnClickListener {
            val exercises = Exercises("some", 5)
            val exercisesList = arrayListOf<Exercises>()
            exercisesList.add(exercises)

            Log.i("Exercises",exercises.toString())

            val trainings = Trainings(UUID.randomUUID(), "MaxTrain", 5, exercisesList)
            val trainingsList = arrayListOf<Trainings>()
            trainingsList.add(trainings)

            Log.i("Trainings", trainings.toString())
            val user = Users(UUID.randomUUID(), "st@mail.ru", "1234", "Max", "Man", 5, 80, 190.0, trainingsList)

            val food = Food()
            food.name = "st"
            food.callories=1.0
            Log.i("Food", food.name+food.callories)

            Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
            Log.i("User", user.toString())
        }
    }
}
