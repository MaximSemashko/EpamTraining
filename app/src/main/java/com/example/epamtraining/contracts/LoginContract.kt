package com.example.epamtraining.contracts

import com.example.epamtraining.models.UserLogin

interface LoginContract {

    interface Repository {
        fun parseResponse(user: UserLogin, url: String): Boolean
    }

    interface View {
        fun validate(): Boolean
        fun showProgress()
        fun hideProgress()
        fun startRegistrationActivity()
        fun initUser(): UserLogin
        fun onFaileParse()
        fun onSuccessParse()
    }

    interface Presenter {
        fun onSignInWasClicked()
        fun onSignUpWasClicked()
    }
}