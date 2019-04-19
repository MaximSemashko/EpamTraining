package com.example.epamtraining.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.Callbacks.ExerciseTouchCallback

import com.example.epamtraining.R
import com.example.epamtraining.adapters.TrainingsAdapter
import com.example.epamtraining.models.Exercises
import kotlinx.android.synthetic.main.fragment_trainings.*
import java.util.*

class TrainingsFragment : Fragment() {

    lateinit private var trainingsAdapter: TrainingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_trainings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trainingsAdapter = TrainingsAdapter()
        val itemTouchHelper = ItemTouchHelper(ExerciseTouchCallback(trainingsAdapter))

        exercisesRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = trainingsAdapter
            itemTouchHelper.attachToRecyclerView(this)
            itemAnimator = object : DefaultItemAnimator() {
                override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
                    return super.animateMove(holder, fromX, fromY, toX, toY)
                }
            }
            addItemDecoration((DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)))
        }

        addExerciseButton?.setOnClickListener {
            val name = exerciseNameText?.text.toString()
            val sets = Integer.parseInt(exerciseSetsText?.text.toString())
            trainingsAdapter.setItem(Exercises(UUID.randomUUID(), name, sets, 1.0))
            exerciseNameText?.setText(null)
            exerciseSetsText?.setText(null)
        }
    }
}
