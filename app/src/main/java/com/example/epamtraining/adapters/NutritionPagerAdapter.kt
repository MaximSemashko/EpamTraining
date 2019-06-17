package com.example.epamtraining.adapters

import android.content.Context
import androidx.annotation.Nullable
import com.example.epamtraining.R
import com.example.epamtraining.fragments.IngestionFragment
import com.example.epamtraining.network.FirebaseAuth


class NutritionPagerAdapter(context: Context?, fm: androidx.fragment.app.FragmentManager?) : androidx.fragment.app.FragmentPagerAdapter(fm!!) {

    val tabTitles = context?.resources?.getStringArray(R.array.tab_title)

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        val fragment: androidx.fragment.app.Fragment
        when (position) {
            0 -> {
                fragment = IngestionFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Breakfast.json")
            }
            1 -> {
                fragment = IngestionFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Lunch.json")
            }
            2 -> {
                fragment = IngestionFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Dinner.json")
            }
            else -> {
                fragment = IngestionFragment()
                fragment.setUrl("https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Breakfast.json")
            }
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles?.get(position)
    }
}