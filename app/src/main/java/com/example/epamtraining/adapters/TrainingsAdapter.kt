package com.example.epamtraining.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.models.Exercises
import kotlinx.android.synthetic.main.exercise_item.view.*
import java.util.*

class TrainingsAdapter(context: Context?) : RecyclerView.Adapter<TrainingsAdapter.TrainingsViewHolder>() {

    private val exercisesList = ArrayList<Exercises>()
    private val layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TrainingsViewHolder {
        return TrainingsViewHolder(layoutInflater.inflate(R.layout.exercise_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: TrainingsViewHolder, i: Int) {
        viewHolder.bind(exercisesList.get(i))
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    fun setItem(exercise: Exercises) {
        exercisesList.add(exercise)
        notifyDataSetChanged()
    }

    class TrainingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(exercises: Exercises) {
            itemView.exerciseNameTextView.text = exercises.name
            itemView.exerciseSetsTextView.text = exercises.repeats.toString()
        }
    }
}
