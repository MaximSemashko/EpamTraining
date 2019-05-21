package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.epamtraining.R
import com.example.epamtraining.fragments.ProfileFragment
import com.example.epamtraining.fragments.TrainingsFragment
import com.example.epamtraining.fragments.UsersFragment
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseAuth.token
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        initDrawer()

        thread {
            FirebaseAuth.getAccountInfo(token)
        }
    }

    fun initDrawer() {
        mainNavigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.setChecked(true)
            drawerLayout.closeDrawers()
            selectDrawerItem(menuItem)

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START) else super.onBackPressed()
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
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
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    companion object {
        fun startMainActivity(packageContext: Context): Intent {
            val intent = Intent(packageContext, MainActivity::class.java).apply {
                //                putExtra(EXTRA_MESSAGE, localId)
            }
            return intent
        }

        private const val EXTRA_MESSAGE = "com.example.epamtraining.activities.MainActivity"
    }
}