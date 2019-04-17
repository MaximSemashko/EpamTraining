package com.example.epamtraining.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.example.epamtraining.fragments.ProfileFragment
import com.example.epamtraining.fragments.TrainingsFragment
import com.example.epamtraining.fragments.UsersFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var currentState: Int = 0

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.users_list_item -> {
                addFragment(UsersFragment())

                return@OnNavigationItemSelectedListener true
            }
            R.id.trainings_list_item -> {
                addFragment(TrainingsFragment())

                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_item -> {
                addFragment(ProfileFragment())

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState != null) mainBottomNavigationView.setSelectedItemId(currentState)
       // mainBottomNavigationView.setSelectedItemId(R.id.trainings_list_item)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        currentState = mainBottomNavigationView.getSelectedItemId();
    }

    override fun onResume() {
        super.onResume()
        mainBottomNavigationView.setSelectedItemId(currentState)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.container_layout, fragment)
                .commit()
    }
}