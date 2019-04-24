package com.example.epamtraining.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.epamtraining.R
import com.example.epamtraining.fragments.ProfileFragment
import com.example.epamtraining.fragments.TrainingsFragment
import com.example.epamtraining.fragments.UsersFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar();

        initDrawer();
    }

    fun initDrawer() {
        mainNavigationView.setNavigationItemSelectedListener(
                object : NavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                        menuItem.setChecked(true)
                        drawerLayout.closeDrawers()
                        selectDrawerItem(menuItem)

                        return true
                    }
                })
    }


    fun selectDrawerItem(menuItem: MenuItem) {
        val fragment: Fragment
        val fragmentClass: Class<*>

        when (menuItem.itemId) {
            R.id.drawerProfile -> fragmentClass = ProfileFragment::class.java
            R.id.drawerTrainingsList -> fragmentClass = TrainingsFragment::class.java
            R.id.drawerUsersList -> fragmentClass = UsersFragment::class.java
            else -> fragmentClass = ProfileFragment::class.java
        }

        fragment = fragmentClass.newInstance() as Fragment
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragment).commit()
    }

    private fun initToolbar() {
        setSupportActionBar(mainToolbar)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }
}