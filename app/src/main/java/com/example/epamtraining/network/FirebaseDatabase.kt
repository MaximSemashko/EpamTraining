package com.example.epamtraining.network

import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth.localId
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request

object FirebaseDatabase {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getUserInfo(): User {
        val request = Request.Builder()
                .url("https://ksport-8842a.firebaseio.com/users/$localId.json")
                .get()
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        val user = gson.fromJson(responseString, User::class.java)
        return user
    }

}