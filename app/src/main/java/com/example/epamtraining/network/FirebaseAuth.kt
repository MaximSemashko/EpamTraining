package com.example.epamtraining.network

import com.example.epamtraining.Constants
import com.example.epamtraining.Constants.Companion.JSON
import com.example.epamtraining.activities.LoginActivity
import com.example.epamtraining.models.Users
import com.google.gson.Gson
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import java.io.IOException
import java.net.URL

object FirebaseAuth {

    private val client = OkHttpClient()
    private val gson = Gson()
    private var token: String? = null
    var localId: String? = null

    @Throws(IOException::class)
    fun signIn(user: LoginActivity.UserLogin, callback: Callback) {

        var jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(JSON, jsonString)
        val URL = URL(Constants.BASE_URL + Constants.OPERATION_VERIFY_PASSWORD + "?key=" + Constants.firebaseKey)
        val request = Request.Builder()
                .url(URL)
                .post(body)
                .build()

        client.newCall(request).enqueue(callback)
    }

    @Throws(IOException::class)
    fun signUp(user: Users) {
        var jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(JSON, jsonString)
        val URL = URL(Constants.BASE_URL + Constants.OPERATION_SIGN_UP_USER + "?key=" + Constants.firebaseKey)
        val request = Request.Builder()
                .url(URL)
                .post(body)
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()
        val rootJsonObject = JSONObject(responseString)
        token = rootJsonObject.get("idToken").toString()
        localId = rootJsonObject.get("localId").toString()
    }

    @Throws(IOException::class)
    fun putUserToRealtimeDatabase(user: Users) {
        var jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(JSON, jsonString)
        val url = "https://ksport-8842a.firebaseio.com/users/$localId.json"
        val request = Request.Builder()
                .url(url)
                .put(body)
                .build()

        client.newCall(request).execute()
    }

}