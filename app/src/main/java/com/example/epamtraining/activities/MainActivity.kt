package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.epamtraining.R
import com.example.epamtraining.activities.LoginActivity.Companion.startAuth
import com.example.epamtraining.contracts.MainContract
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.presenters.MainPresenter
import com.example.epamtraining.repositories.MainRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), MainContract.View {

    private val mainRepository = MainRepository()
    private val mainPresenter = MainPresenter(this, supportFragmentManager, this, mainRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        initDrawer()

        mainPresenter.checkCurrentUserToken()
    }

    override fun initProfile() {
        mainNavigationView.setCheckedItem(R.id.drawerProfile)
        mainPresenter.selectDrawerItem(mainNavigationView.menu.getItem(0))
    }

    override fun initDrawer() {
        mainNavigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.setChecked(true)
            drawerLayout.closeDrawers()
            mainPresenter.selectDrawerItem(menuItem)
            true
        }
    }

    override fun detachMainActivity() {
        finish()
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

    override fun logout() {
        removeAllFragments()
        finish()
        FirebaseAuth.signOut()
        startAuth(this@MainActivity)
    }

    override fun initToolbar() {
        setSupportActionBar(mainToolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    override fun removeAllFragments() {
        val fragmentManager = supportFragmentManager
        while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        }

    }

    companion object {
        fun startMainActivity(packageContext: Context?) {
            val intent = Intent(packageContext, MainActivity::class.java)
            packageContext?.startActivity(intent)
        }
    }
}