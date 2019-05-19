package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.google.gson.Gson
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import kotlin.concurrent.thread


class LoginActivity : AppCompatActivity() {

    data class UserLogin(val email: String, val password: String)

    private var localId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()
            thread {
                signIn(UserLogin(email,password))
            }
        }
    }

    var client = OkHttpClient()

    @Throws(IOException::class)
    fun signIn(user: UserLogin) {
        val gson = Gson()
        var jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(JSON, jsonString)
        val URL = URL(BASE_URL + OPERATION_VERIFY_PASSWORD + "?key=" + firebaseKey)
        val request = Request.Builder()
                .url(URL)
                .post(body)
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()
        val rootJsonObject = JSONObject(responseString)
        localId = rootJsonObject.get("localId").toString()
        println("User localId $localId")
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        private const val firebaseKey = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        private const val OPERATION_VERIFY_PASSWORD = "verifyPassword"
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }
}
