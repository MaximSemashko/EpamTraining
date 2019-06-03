package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.epamtraining.R
import com.example.epamtraining.activities.MainActivity.Companion.startMainActivity
import com.example.epamtraining.contracts.RegistrationContract
import com.example.epamtraining.models.User
import com.example.epamtraining.presenters.RegistrationPresenter
import com.example.epamtraining.repositories.RegistrationRepository
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*


class RegistrationActivity : AppCompatActivity(), RegistrationContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val registrationRepository = RegistrationRepository()
        val registrationPresenter = RegistrationPresenter(registationRepository = registrationRepository, registrationView = this)
        registerButton.setOnClickListener {
            registrationPresenter.onSignUpWasClicked()
        }
    }

    override fun hideProgress() {
        if (registrationProgressBar.visibility != View.GONE) {
            registrationProgressBar.visibility = View.GONE
        }
    }

    override fun showProgress() {
        if (registrationProgressBar.visibility != View.VISIBLE) {
            registrationProgressBar.visibility = View.VISIBLE
        }
    }

    override fun initUser(): User {
        val id = UUID.randomUUID().toString()
        val email = usersEmailEditText.text.toString()
        val name = usersNameEditText.text.toString()
        val password = usersPasswordEditText.text.toString()
        val sex = usersSexSpinner.selectedItem.toString()
        val age = Integer.parseInt(usersAgeEditText.text.toString())
        val weight = usersWeightEditText.text.toString().toDouble()
        val height = usersHeightEditText.text.toString().toDouble()

        return User(id, email, password, name, sex, age, weight, height)
    }

    override fun onFailedParse() {
        runOnUiThread {
            hideProgress()
            usersNameEditText.error = "Please check your data"
        }
    }

    override fun onSuccessParse() {
        runOnUiThread {
            hideProgress()
            startMainActivity(this@RegistrationActivity)
        }
    }

    override fun validate(): Boolean {
        var valid = true

        val email = usersEmailEditText.text.toString()
        val password = usersPasswordEditText.text.toString()
        val repeatedPassword = repeatPassword.text.toString()
        val name = usersNameEditText.text.toString()
        val sex = usersSexSpinner.selectedItem.toString()
        val weight = usersWeightEditText.text.toString()
        val height = usersHeightEditText.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usersEmailEditText.error = "Enter a valid email address"
            valid = false
        } else {
            usersEmailEditText.error = null
        }

        if (password.isEmpty() || password.length < 6) {
            usersPasswordEditText.error = "Enter password more than 6"
            valid = false
        } else {
            usersPasswordEditText.error = null
        }

        if (password != repeatedPassword) {
            usersPasswordEditText.error = "Passwords arent the same"
            repeatPassword.error = "Passwords arent the same"
            valid = false
        } else {
            usersPasswordEditText.error = null
        }

        if (name.isEmpty()) {
            usersNameEditText.error = "Please, enter the name"
            valid = false
        } else {
            usersNameEditText.error = null
        }

        if (sex.isEmpty()) {
            valid = false
        }

        if (weight.isEmpty()) {
            usersWeightEditText.error = "Please, enter correct weight"
            valid = false
        } else {
            usersWeightEditText.error = null
        }

        if (height.isEmpty()) {
            usersHeightEditText.error = "Please, enter correct height"
            valid = false
        } else {
            usersHeightEditText.error = null
        }

        return valid
    }

    companion object {
        fun startRegistration(packageContext: Context) {
            val intent = Intent(packageContext, RegistrationActivity::class.java)
            packageContext.startActivity(intent)
        }
    }
}
