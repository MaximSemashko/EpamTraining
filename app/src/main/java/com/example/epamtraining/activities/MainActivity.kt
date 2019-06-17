package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.epamtraining.R
import com.example.epamtraining.activities.LoginActivity.Companion.startAuth
import com.example.epamtraining.contracts.MainContract
import com.example.epamtraining.customViews.HeaderView
import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseDatabase
import com.example.epamtraining.presenters.MainPresenter
import com.example.epamtraining.repositories.MainRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.concurrent.thread


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

    override fun initHeaderView() {
        val headerView = mainNavigationView
                .getHeaderView(0)
                .findViewById<HeaderView>(R.id.main_header_view)

        thread {
            val user: User? = FirebaseDatabase.getUserInfo()
            runOnUiThread {
                headerView.initViews(user?.name.toString())
            }
        }
    }

    override fun initProfile() {
        mainNavigationView.setCheckedItem(R.id.drawerProfile)
        mainPresenter.selectDrawerItem(mainNavigationView.menu.getItem(0))
    }

    override fun initDrawer() {
        mainNavigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START) else drawerLayout.openDrawer(GravityCompat.START)
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