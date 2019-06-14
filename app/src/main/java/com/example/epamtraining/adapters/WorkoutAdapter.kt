package com.example.epamtraining.adapters

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.epamtraining.R
import com.example.epamtraining.models.Trainings

class WorkoutAdapter(context: Context?, private var url: String) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val trainingsList = ArrayList<Trainings>()
    private val layoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(trainingsList[position])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): WorkoutViewHolder {
        return WorkoutViewHolder(layoutInflater.inflate(R.layout.training_item, viewGroup, false))
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
        private var name: TextView? = null
        private var type: TextView? = null
        private var duration: TextView? = null
        private var layout: ConstraintLayout? = null

        init {
            name = itemView.findViewById(R.id.trainingNameTextView)
            type = itemView.findViewById(R.id.trainingTypeTextView)
            duration = itemView.findViewById(R.id.trainingDurationTextView)
            layout = itemView.findViewById(R.id.workoutItemLayout)
        }

        fun bind(trainings: Trainings) {
            name?.text = trainings.name
            type?.text = trainings.type
            duration?.text = trainings.duration
            selectBackground(trainings.type)
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun selectBackground(type: String?) {
            when {
                type.equals("cardio") -> layout?.background = itemView.context.getDrawable(R.drawable.cardio_training)
                type.equals("aerobic") -> layout?.background = itemView.context.getDrawable(R.drawable.aerobic_training)
                type.equals("power") -> layout?.background = itemView.context.getDrawable(R.drawable.power_training)
            }
        }
    }
}
