package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.example.epamtraining.Util
import com.example.epamtraining.models.Users
import com.example.epamtraining.network.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*
import java.util.concurrent.Executors


class RegistrationActivity : AppCompatActivity() {

    private val executor = Executors.newCachedThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerButton.setOnClickListener {
            if (validate()) {
                Util.showProgress(registrationProgressBar)
                val user = Users(id = UUID.randomUUID().toString(),
                        email = usersEmailEditText.text.toString(),
                        name = usersNameEditText.text.toString(),
                        password = usersPasswordEditText.text.toString(),
                        sex = usersSexSpinner.selectedItem.toString(),
                        age = Integer.parseInt(usersAgeEditText.text.toString()),
                        weight = usersWeightEditText.text.toString().toDouble(),
                        height = usersHeightEditText.text.toString().toDouble())

                executor.execute {
                    FirebaseAuth.apply {
                        signUp(user)
                        putUserToRealtimeDatabase(user)
                    }
                    runOnUiThread {
                        Util.hideProgress(registrationProgressBar)
                        startActivity(MainActivity.startMainActivity(this))
                    }
                }
            }
        }
    }

    fun validate(): Boolean {
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
            usersEmailEditText.setError(null)
        }

        if (password.isEmpty() || password.length < 6) {
            usersPasswordEditText.error = "Enter password more than 6"
            valid = false
        } else {
            usersPasswordEditText.setError(null)
        }

        if (password != repeatedPassword) {
            usersPasswordEditText.error = "Passwords arent the same"
            repeatPassword.error = "Passwords arent the same"
            valid = false
        } else {
            usersPasswordEditText.setError(null)
        }

        if (name.isEmpty()) {
            usersNameEditText.error = "Please, enter the name"
            valid = false
        } else {
            usersNameEditText.setError(null)
        }

        if (sex.isEmpty()) {
            valid = false
        }

        if (weight.isEmpty()) {
            usersWeightEditText.error = "Please, enter correct weight"
            valid = false
        } else {
            usersWeightEditText.setError(null)
        }

        if (height.isEmpty()) {
            usersHeightEditText.error = "Please, enter correct height"
            valid = false
        } else {
            usersHeightEditText.setError(null)
        }

        return valid
    }

    companion object {
        fun startRegistration(packageContext: Context): Intent {
            val intent = Intent(packageContext, RegistrationActivity::class.java)
            return intent
        }
    }
}
