package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.epamtraining.R
import com.example.epamtraining.activities.LoginActivity.Companion.startAuth
import com.example.epamtraining.fragments.NutritionFragment
import com.example.epamtraining.fragments.PostsFragment
import com.example.epamtraining.fragments.ProfileFragment
import com.example.epamtraining.fragments.TrainingsFragment
import com.example.epamtraining.network.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        initDrawer()

        if (FirebaseAuth.token == null) {
            finish()
            startActivity(startAuth(this))
        } else {
            mainNavigationView.setCheckedItem(R.id.drawerProfile)
            selectDrawerItem(mainNavigationView.menu.getItem(0))
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
        val fragment: Fragment?
        val fragmentClass: Class<*>?

        when (menuItem.itemId) {
            R.id.drawerProfile -> fragmentClass = ProfileFragment::class.java
            R.id.drawerNutrition -> fragmentClass = NutritionFragment::class.java
            R.id.drawerTrainingsList -> fragmentClass = TrainingsFragment::class.java
            R.id.drawerPosts -> fragmentClass = PostsFragment::class.java
            R.id.drawerLogout -> {
                fragmentClass = ProfileFragment::class.java
                removeAllFragments(supportFragmentManager)
                FirebaseAuth.signOut()
                finish()
                startActivity(startAuth(this@MainActivity))
            }

            else -> fragmentClass = ProfileFragment::class.java
        }

        fragment = fragmentClass.newInstance() as Fragment
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
    }

    private fun initToolbar() {
        setSupportActionBar(mainToolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    private fun removeAllFragments(fragmentManager: FragmentManager) {
        while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        }

    }

    companion object {
        fun startMainActivity(packageContext: Context): Intent {
            val intent = Intent(packageContext, MainActivity::class.java)
            return intent
        }
    }
}