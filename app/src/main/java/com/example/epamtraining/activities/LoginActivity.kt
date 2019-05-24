package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.Constants
import com.example.epamtraining.R
import com.example.epamtraining.Util
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseAuth.localId
import com.example.epamtraining.network.FirebaseAuth.token
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread


class LoginActivity : AppCompatActivity() {

    data class UserLogin(val email: String, val password: String)

    private val url = Constants.BASE_URL + Constants.OPERATION_VERIFY_PASSWORD + "?key=" + Constants.firebaseKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            if (validate()) {
                Util.showProgress(loginProgressBar)
                thread {
                    FirebaseAuth
                            .userAuth(UserLogin(email, password), url, object : Callback {
                                override fun onResponse(response: Response?) {
                                    if (response!!.code() != 400) {
                                        val responseString = response?.body()?.string()
                                        val rootJsonObject = JSONObject(responseString)
                                        localId = rootJsonObject.get("localId").toString()
                                        token = rootJsonObject.get("idToken").toString()
                                        runOnUiThread {
                                            Util.hideProgress(loginProgressBar)
                                            startActivity(MainActivity.startMainActivity(this@LoginActivity))
                                            finish()
                                        }
                                    } else {
                                        runOnUiThread {
                                            Util.hideProgress(loginProgressBar)
                                            loginEmailEditText.error = "Please check your data"
                                        }
                                    }
                                }

                                override fun onFailure(request: Request?, e: IOException?) {
                                    Util.hideProgress(loginProgressBar)
                                }
                            })
                }
            }
        }

        registrationLinkTextView.setOnClickListener {
            startActivity(RegistrationActivity.startRegistration(this))
        }
    }

    fun validate(): Boolean {
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

    companion object {
        fun startAuth(packageContext: Context): Intent {
            val intent = Intent(packageContext, LoginActivity::class.java)
            return intent
        }
    }
}
