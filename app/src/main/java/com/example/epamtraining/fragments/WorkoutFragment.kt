package com.example.epamtraining.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.epamtraining.R
import com.example.epamtraining.activities.ProductsActivity
import com.example.epamtraining.adapters.WorkoutAdapter
import com.example.epamtraining.models.Trainings
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_ingestion.*
import kotlinx.android.synthetic.main.fragment_ingestion.workoutProgressBar
import kotlinx.android.synthetic.main.fragment_ingestion.workoutRecyclerView
import kotlinx.android.synthetic.main.fragment_workout.*
import kotlin.concurrent.thread

class WorkoutFragment : Fragment() {

    private var trainings: List<Trainings> = ArrayList()
    private lateinit var url: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_workout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workoutAdapter = WorkoutAdapter(context, url)

        workoutRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = workoutAdapter
        }

        showProgress()
        thread {
            FirebaseDatabase.getTrainings()
            //TODO
//            trainings = FirebaseDatabase.getItems(url)

            activity?.runOnUiThread {
                workoutAdapter.updateItems(trainings)
//                totalCaloriesTextView.text = workoutAdapter.getMealCalories().toString()
                hideProgress()
            }
        }

        workoutSwipeRefreshLayout.apply {
            setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

            setOnRefreshListener {
                thread {
                    //TODO
//                    trainings = FirebaseDatabase.getItems(url)

                    activity?.runOnUiThread {
                        workoutAdapter.updateItems(trainings)
//                        totalCaloriesTextView.text = workoutAdapter.getMealCalories().toString()
                        isRefreshing = false
                    }
                }
                activity?.runOnUiThread {
                    workoutAdapter.updateItems(trainings)

                }
            }
        }
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun hideProgress() {
        if (workoutProgressBar.visibility != View.GONE) {
            workoutProgressBar.visibility = View.GONE
        }
    }

    fun showProgress() {
        if (workoutProgressBar.visibility != View.VISIBLE) {
            workoutProgressBar.visibility = View.VISIBLE
        }
    }
}
