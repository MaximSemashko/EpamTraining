package com.example.epamtraining.network

import com.example.epamtraining.Constants.Companion.JSON
import com.google.gson.Gson
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import java.io.IOException

object FirebaseAuth {

    private val client = OkHttpClient()
    private val gson = Gson()
    var token: String? = null
    var localId: String? = null

    @Throws(IOException::class)
    fun <T> userAuth(t:T,url:String, callback: Callback) {
        var jsonString = gson.toJson(t).toString()
        val body = RequestBody.create(JSON, jsonString)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

        client.newCall(request).enqueue(callback)
    }
}