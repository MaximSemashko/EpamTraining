package com.example.epamtraining.contracts

import com.example.epamtraining.models.User

interface RegistrationContract {

    interface Repository {
        fun parseResponse(user: User, url: String): Boolean
    }

    interface View {
        fun validate(): Boolean
        fun showProgress()
        fun hideProgress()
        fun initUser(): User
        fun onFailedParse()
        fun onSuccessParse()
    }

    interface Presenter {
        fun onSignUpWasClicked()
    }
}