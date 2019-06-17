package com.example.epamtraining.presenters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.MenuItem
import com.example.epamtraining.R
import com.example.epamtraining.activities.LoginActivity
import com.example.epamtraining.contracts.MainContract
import com.example.epamtraining.fragments.NutritionFragment
import com.example.epamtraining.fragments.PostsFragment
import com.example.epamtraining.fragments.ProfileFragment
import com.example.epamtraining.fragments.TrainingsFragment

class MainPresenter(val context: Context,
                    private val fragmentManager: FragmentManager,
                    val view: MainContract.View,
                    val repository: MainContract.Repository) : MainContract.Presenter {

    override fun checkCurrentUserToken() {
        if (repository.isUserOnline()) {
            view.detachMainActivity()
            LoginActivity.startAuth(packageContext = context)
        } else {
            view.initProfile()
            view.initHeaderView()
        }
    }

    override fun selectDrawerItem(menuItem: MenuItem) {
        val fragment: Fragment?
        val fragmentClass: Class<*>?

        when (menuItem.itemId) {
            R.id.drawerProfile -> fragmentClass = ProfileFragment::class.java
            R.id.drawerNutrition -> fragmentClass = NutritionFragment::class.java
            R.id.drawerTrainingsList -> fragmentClass = TrainingsFragment::class.java
            R.id.drawerPosts -> fragmentClass = PostsFragment::class.java
            R.id.drawerLogout -> {
                fragmentClass = ProfileFragment::class.java
                view.logout()
            }

            else -> fragmentClass = ProfileFragment::class.java
        }
        fragment = fragmentClass.newInstance() as Fragment

        fragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}