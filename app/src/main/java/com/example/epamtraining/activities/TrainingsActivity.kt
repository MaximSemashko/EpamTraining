package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.EditText
import android.widget.ImageButton
import com.example.epamtraining.Callbacks.ExerciseTouchCallback
import com.example.epamtraining.R
import com.example.epamtraining.adapters.TrainingsAdapter
import com.example.epamtraining.models.Exercises

class TrainingsActivity : AppCompatActivity() {

    private var addExerciseButton: ImageButton? = null
    private var exerciseNameText: EditText? = null
    private var exerciseSetsText: EditText? = null

    private var recyclerView: RecyclerView? = null
    private var trainingsAdapter: TrainingsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainings)

        addExerciseButton = findViewById(R.id.add_exercise_button)
        exerciseNameText = findViewById(R.id.exercise_name_text)
        exerciseSetsText = findViewById(R.id.exercise_sets_text)

        recyclerView = findViewById(R.id.exercises)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        trainingsAdapter = TrainingsAdapter()
        recyclerView!!.adapter = trainingsAdapter
        val itemTouchHelper = ItemTouchHelper(ExerciseTouchCallback(trainingsAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView!!.itemAnimator = object : DefaultItemAnimator() {
            override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
                return super.animateMove(holder, fromX, fromY, toX, toY)
            }
        }
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        addExerciseButton!!.setOnClickListener {
            val name = exerciseNameText!!.text.toString()
            val sets = Integer.parseInt(exerciseSetsText!!.text.toString())
            trainingsAdapter!!.setItem(Exercises(name,sets,1.0))
            exerciseNameText!!.setText(null)
            exerciseSetsText!!.setText(null)
        }
    }
}
