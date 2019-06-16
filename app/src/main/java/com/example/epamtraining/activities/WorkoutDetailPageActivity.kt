package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ExercisesAdapter
import com.example.epamtraining.models.Exercises
import com.example.epamtraining.models.Trainings
import kotlinx.android.synthetic.main.activity_workout_detail_page.*
import java.io.Serializable
import java.util.*

class WorkoutDetailPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail_page)

        val training = getTrainingInfo(intent)
        Log.i("TAG", "Training $training")

        initViews(training)
    }

    private fun initViews(training: Trainings?) {
        workoutNameTextView.text = training?.name
        workoutDurationTextView.text = training?.duration
        workoutTypeTextView.text = training?.type

        val exercisesAdapter = training?.exercisesList?.let { ExercisesAdapter(context = applicationContext, exercisesList = it) }

        workoutRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exercisesAdapter
        }
    }

    private fun getTrainingInfo(intent: Intent): Trainings? {
        intent.apply {
            val id: Int? = getIntExtra(TRAINING_ID, 1)
            val type: String? = getStringExtra(TRAINING_TYPE)
            val duration: String? = getStringExtra(TRAINING_DURATION)
            val name: String? = getStringExtra(TRAINING_NAME)
            val exercises: Serializable? = getSerializableExtra(TRAINING_EXERCISES)

            return Trainings(id, name, type, duration, exercises as ArrayList<Exercises>?)
        }
    }

    companion object {
        fun startWorkoutPage(context: Context?, training: Trainings) {
            val intent = Intent(context, WorkoutDetailPageActivity::class.java)

            intent.apply {
                putExtra(TRAINING_ID, training.id)
                putExtra(TRAINING_TYPE, training.type)
                putExtra(TRAINING_DURATION, training.duration)
                putExtra(TRAINING_NAME, training.name)
                putExtra(TRAINING_EXERCISES, training.exercisesList)
            }

            context?.startActivity(intent)
        }

        const val TRAINING_TYPE = "type"
        const val TRAINING_ID = "id"
        const val TRAINING_DURATION = "duration"
        const val TRAINING_NAME = "name"
        const val TRAINING_EXERCISES = "exercises"
    }
}
