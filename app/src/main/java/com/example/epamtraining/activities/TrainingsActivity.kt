package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.epamtraining.Callbacks.ExerciseTouchCallback
import com.example.epamtraining.R
import com.example.epamtraining.adapters.TrainingsAdapter
import com.example.epamtraining.models.Exercises
import kotlinx.android.synthetic.main.fragment_trainings.*
import java.util.*

class TrainingsActivity : AppCompatActivity() {

    lateinit private var trainingsAdapter: TrainingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_trainings)

        exercisesRecyclerView?.layoutManager = LinearLayoutManager(this)
        trainingsAdapter = TrainingsAdapter()
        exercisesRecyclerView?.adapter = trainingsAdapter
        val itemTouchHelper = ItemTouchHelper(ExerciseTouchCallback(trainingsAdapter))
        itemTouchHelper.attachToRecyclerView(exercisesRecyclerView)
        exercisesRecyclerView?.itemAnimator = object : DefaultItemAnimator() {
            override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
                return super.animateMove(holder, fromX, fromY, toX, toY)
            }
        }
        exercisesRecyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        addExerciseButton?.setOnClickListener {
            val name = exerciseNameText?.text.toString()
            val sets = Integer.parseInt(exerciseSetsText?.text.toString())
            trainingsAdapter.setItem(Exercises(UUID.randomUUID(), name, sets, 1.0))
            exerciseNameText?.setText(null)
            exerciseSetsText?.setText(null)
        }
    }
}
