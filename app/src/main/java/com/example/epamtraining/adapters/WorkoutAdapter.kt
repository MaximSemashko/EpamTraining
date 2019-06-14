package com.example.epamtraining.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.epamtraining.R
import com.example.epamtraining.models.Trainings
import kotlinx.android.synthetic.main.product_item.view.*

class WorkoutAdapter(context: Context?, private var url: String) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val trainingsList = ArrayList<Trainings>()
    private val layoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(trainingsList[position])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): WorkoutViewHolder {
        return WorkoutViewHolder(layoutInflater.inflate(R.layout.product_item, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return trainingsList.size
    }

    fun updateItems(trainings: List<Trainings>) {
        trainingsList.clear()
        trainingsList.addAll(trainings)
        notifyDataSetChanged()
    }

    fun addItem(trainings: Trainings) {
        trainingsList.add(trainings)
        notifyDataSetChanged()
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(trainings: Trainings) {
            //TODO
            itemView.trainingNameTextView.text = trainings.name
//            itemView.productsCaloriesTextView.text = trainings.calories.toString()
        }
    }
}
