package com.example.epamtraining.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epamtraining.R
import com.example.epamtraining.adapters.TrainingsAdapter
import com.example.epamtraining.adapters.TrainingsPagerAdapter
import kotlinx.android.synthetic.main.fragment_trainings.*

class TrainingsFragment : androidx.fragment.app.Fragment() {

    private lateinit var trainingsAdapter: TrainingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_trainings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trainingsAdapter = TrainingsAdapter(context)

        trainingsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = trainingsAdapter


            val adapter = TrainingsPagerAdapter(context, childFragmentManager)

            trainingsViewPager.adapter = adapter

            trainingsTabLayout.setupWithViewPager(trainingsViewPager)
        }
    }
}
