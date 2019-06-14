package com.example.epamtraining.adapters

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.epamtraining.R
import com.example.epamtraining.fragments.IngestionFragment
import com.example.epamtraining.fragments.WorkoutFragment
import com.example.epamtraining.network.FirebaseAuth


class TrainingsPagerAdapter(context: Context?, fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    private val tabTitles = context?.resources?.getStringArray(R.array.tab_trainings_title)

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment

        when (position) {
            0 -> {
                fragment = WorkoutFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/Trainings.json")
            }
            1 -> {
                fragment = WorkoutFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Trainings.json")
            }
            else -> {
                fragment = WorkoutFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/Trainings.json")
            }
        }

        return fragment
    }

    override fun getCount(): Int {
        return 2
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles?.get(position)
    }
}