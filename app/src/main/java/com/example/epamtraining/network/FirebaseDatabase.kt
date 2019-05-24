package com.example.epamtraining.network

import com.example.epamtraining.Constants
import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth.localId
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import java.io.IOException

object FirebaseDatabase {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val url = "https://ksport-8842a.firebaseio.com/users/$localId.json"

    @Throws(IOException::class)
    fun <T>addToRealtimeDatabase(t: T) {
        var jsonString = gson.toJson(t).toString()
        val body = RequestBody.create(Constants.JSON, jsonString)
        val request = Request.Builder()
                .url(url)
                .put(body)
                .build()

        client.newCall(request).execute()
    }

    fun getUserInfo(): User {
        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        val user = gson.fromJson(responseString, User::class.java)
        return user
    }

}