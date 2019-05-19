package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.example.epamtraining.models.Users
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.concurrent.Executors


class RegistrationActivity : AppCompatActivity() {

    private var token: String? = null
    private var localId: String? = null
    private val executor = Executors.newCachedThreadPool()
    val client = OkHttpClient()
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerButton.setOnClickListener {

            val user = Users(id = UUID.randomUUID().toString(),
                    email = usersEmailEditText.text.toString(),
                    name = usersNameEditText.text.toString(),
                    password = usersPasswordEditText.text.toString(),
                    age = Integer.parseInt(usersAgeEditText.text.toString()),
                    weight = usersWeightEditText.text.toString().toDouble(),
                    height = usersHeightEditText.text.toString().toDouble())

            executor.execute {
                signUp(user)
                putUserToRealtimeDatabase(user)
            }
        }
    }

    @Throws(IOException::class)
    fun signUp(user: Users) {
        var jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(LoginActivity.JSON, jsonString)
        val URL = URL(BASE_URL + OPERATION_SIGN_UP_USER + "?key=" + firebaseKey)
        val request = Request.Builder()
                .url(URL)
                .post(body)
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()
        val rootJsonObject = JSONObject(responseString)
        token = rootJsonObject.get("idToken").toString()
        localId = rootJsonObject.get("localId").toString()
        println("User localId $localId")
        println("User token $token")
    }

    @Throws(IOException::class)
    fun putUserToRealtimeDatabase(user: Users) {
        var jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(LoginActivity.JSON, jsonString)
        val url = "https://ksport-8842a.firebaseio.com/users/$localId.json"
        val request = Request.Builder()
                .url(url)
                .put(body)
                .build()

        client.newCall(request).execute()
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        private const val firebaseKey = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        private const val OPERATION_SIGN_UP_USER = "signupNewUser"
    }
}