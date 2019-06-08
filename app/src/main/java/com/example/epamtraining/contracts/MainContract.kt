package com.example.epamtraining.contracts

import android.view.MenuItem

interface MainContract {

    interface Repository {
        fun isUserOnline(): Boolean
    }

    interface View {
        fun initToolbar()
        fun initDrawer()
        fun removeAllFragments()
        fun logout()
        fun initProfile()
        fun detachMainActivity()
        fun onBackPressed()
        fun onOptionsItemSelected(item: MenuItem): Boolean
    }

    interface Presenter {
        fun selectDrawerItem(menuItem: MenuItem)
        fun checkCurrentUserToken()
    }
}