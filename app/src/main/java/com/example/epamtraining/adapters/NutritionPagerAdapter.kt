package com.example.epamtraining.adapters

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.epamtraining.R
import com.example.epamtraining.fragments.BreakfastFragment
import com.example.epamtraining.fragments.DinnerFragment
import com.example.epamtraining.fragments.LunchFragment


class NutritionPagerAdapter(context: Context?, fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    val tabTitles = context?.resources?.getStringArray(R.array.tab_title)

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        when (position) {

            0 -> fragment = BreakfastFragment()
            1 -> fragment = LunchFragment()
            2 -> fragment = DinnerFragment()
            else -> fragment = BreakfastFragment()
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3;
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles?.get(position)
    }
}