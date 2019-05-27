package com.example.epamtraining.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.adapters.NutritionPagerAdapter
import kotlinx.android.synthetic.main.fragment_nutrition.*


class NutritionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_nutrition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NutritionPagerAdapter(context, fragmentManager)

        nutritionViewPager.adapter = adapter

        nutritionTabLayout.setupWithViewPager(nutritionViewPager)
    }
}
