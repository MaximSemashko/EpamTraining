package com.example.epamtraining.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.epamtraining.R
import com.example.epamtraining.models.Exercises
import kotlinx.android.synthetic.main.exercise_item.view.*
import java.util.*

class ExercisesAdapter(val context: Context?, val exercisesList: ArrayList<Exercises>) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(layoutInflater.inflate(R.layout.exercise_item, parent, false))
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exercisesList.get(position))
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(exercises: Exercises?) {
            itemView.workoutNameTextView.text = exercises?.name
            itemView.numberOfRepeatsTextView.text = exercises?.repeats.toString()
            itemView.exerciseCaloriesTextView.text = exercises?.calories.toString()
        }
    }
}