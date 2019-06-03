package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.epamtraining.R
import com.example.epamtraining.activities.MainActivity.Companion.startMainActivity
import com.example.epamtraining.activities.RegistrationActivity.Companion.startRegistration
import com.example.epamtraining.contracts.LoginContract
import com.example.epamtraining.models.UserLogin
import com.example.epamtraining.presenters.LoginPresenter
import com.example.epamtraining.repositories.LoginRepository
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginRepository = LoginRepository()
        val loginPresenter = LoginPresenter(loginRepository = loginRepository, loginView = this)

        signInButton.setOnClickListener {
            loginPresenter.onSignInWasClicked()
        }

        registrationLinkTextView.setOnClickListener {
            loginPresenter.onSignUpLinkWasClicked()
        }
    }

    override fun onFailedParse() {
        runOnUiThread {
            hideProgress()
            loginEmailEditText.error = "Please check your data"
        }
    }

    override fun onSuccessParse() {
        runOnUiThread {
            hideProgress()
            startMainActivity(this@LoginActivity)
        }
    }

    override fun initUser(): UserLogin {
        val email = loginEmailEditText.text.toString()
        val password = loginPasswordEditText.text.toString()
        return UserLogin(email, password)
    }

    override fun validate(): Boolean {
        var valid = true

        val email = loginEmailEditText.text.toString()
        val password = loginPasswordEditText.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmailEditText.error = "Enter a valid email address"
            valid = false
        } else {
            loginEmailEditText.setError(null)
        }

        if (password.isEmpty() || password.length < 6) {
            loginPasswordEditText.error = "Enter password more than 6"
            valid = false
        } else {
            loginPasswordEditText.setError(null)
        }

        return valid
    }

    override fun hideProgress() {
        if (loginProgressBar.visibility != View.GONE) {
            loginProgressBar.visibility = View.GONE
        }
    }

    override fun showProgress() {
        if (loginProgressBar.visibility != View.VISIBLE) {
            loginProgressBar.visibility = View.VISIBLE
        }
    }

    override fun startRegistrationActivity() {
        startRegistration(this)
    }

    companion object {
        fun startAuth(packageContext: Context?) {
            val intent = Intent(packageContext, LoginActivity::class.java)
            packageContext?.startActivity(intent)
        }
    }
}
