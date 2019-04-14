package com.example.epamtraining.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.example.epamtraining.fragments.ProfileFragment
import com.example.epamtraining.fragments.TrainingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.news_item-> {
                val fragment = ProfileFragment.Companion.newInstance()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.search_item -> {
                val fragment = TrainingsFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = FragmentHome.Companion.newInstance()
        addFragment(fragment)
    }

    /**
     * add/replace fragment in container [FrameLayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }
}