package com.example.epamtraining.presenters

import com.example.epamtraining.Constants
import com.example.epamtraining.contracts.RegistrationContract
import kotlin.concurrent.thread

class RegistrationPresenter(val registrationView: RegistrationContract.View, val registationRepository: RegistrationContract.Repository) : RegistrationContract.Presenter {

    private val url: String = Constants.BASE_URL + Constants.OPERATION_SIGN_UP_USER + "?key=" + Constants.FIREBASE_KEY

    override fun onSignUpWasClicked() {
        val user = registrationView.initUser()
        if (registrationView.validate()) {
            registrationView.showProgress()
            thread {
                val boolean = registationRepository.parseResponse(user, url)
                if (boolean) registrationView.onSuccessParse() else registrationView.onFailedParse()
            }
        }
    }
}
