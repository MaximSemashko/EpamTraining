package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.Constants
import com.example.epamtraining.R
import com.example.epamtraining.Util
import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseDatabase.addToRealtimeDatabase
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject
import java.io.IOException
import java.util.*


class RegistrationActivity : AppCompatActivity() {

    private val url: String = Constants.BASE_URL + Constants.OPERATION_SIGN_UP_USER + "?key=" + Constants.firebaseKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerButton.setOnClickListener {
            if (validate()) {
                Util.showProgress(registrationProgressBar)
                val user = User(id = UUID.randomUUID().toString(),
                        email = usersEmailEditText.text.toString(),
                        name = usersNameEditText.text.toString(),
                        password = usersPasswordEditText.text.toString(),
                        sex = usersSexSpinner.selectedItem.toString(),
                        age = Integer.parseInt(usersAgeEditText.text.toString()),
                        weight = usersWeightEditText.text.toString().toDouble(),
                        height = usersHeightEditText.text.toString().toDouble())

                FirebaseAuth.apply {
                    userAuth(user, url, object : Callback {
                        override fun onResponse(response: Response?) {
                            if (response!!.code() != 400) {
                                val responseString = response?.body()?.string()
                                val rootJsonObject = JSONObject(responseString)

                                localId = rootJsonObject.get("localId").toString()
                                token = rootJsonObject.get("idToken").toString()

                                addToRealtimeDatabase(user)

                                runOnUiThread {
                                    Util.hideProgress(registrationProgressBar)
                                    startActivity(MainActivity.startMainActivity(this@RegistrationActivity))
                                    finish()
                                }
                            } else {
                                runOnUiThread {
                                    Util.hideProgress(registrationProgressBar)
                                    usersEmailEditText.error = "Please check your data"
                                }
                            }
                        }

                        override fun onFailure(request: Request?, e: IOException?) {
                            Util.hideProgress(registrationProgressBar)
                        }
                    })
                }

            }
            runOnUiThread {
                Util.hideProgress(registrationProgressBar)
                startActivity(MainActivity.startMainActivity(this))
            }
        }
    }

    private fun validate(): Boolean {
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
        fun startRegistration(packageContext: Context): Intent {
            return Intent(packageContext, RegistrationActivity::class.java)
        }
    }
}
