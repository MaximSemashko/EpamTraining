package com.example.epamtraining.presenters

import com.example.epamtraining.Constants
import com.example.epamtraining.contracts.LoginContract
import kotlin.concurrent.thread

class LoginPresenter(val loginView: LoginContract.View, val loginRepository: LoginContract.Repository) : LoginContract.Presenter {

    private val url = Constants.BASE_URL + Constants.OPERATION_VERIFY_PASSWORD + "?key=" + Constants.FIREBASE_KEY

    override fun onSignInWasClicked() {
        val user = loginView.initUser()
        if (loginView.validate()) {
            loginView.showProgress()
            thread {
                val boolean = loginRepository.parseResponse(user, url)
                if (boolean) loginView.onSuccessParse() else loginView.onFailedParse()
            }
        }
    }

    override fun onSignUpLinkWasClicked() {
        loginView.startRegistrationActivity()
    }
}