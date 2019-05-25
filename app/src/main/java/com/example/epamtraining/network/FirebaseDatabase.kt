package com.example.epamtraining.network

import com.example.epamtraining.Constants
import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth.localId
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


object FirebaseDatabase {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val usersUrl = "https://ksport-8842a.firebaseio.com/users/$localId.json"
    private val imagesUrl = "https://firebasestorage.googleapis.com/v0/b/ksport-8842a.appspot.com/o/"

    @Throws(IOException::class)
    fun <T> addToRealtimeDatabase(t: T) {
        var jsonString = gson.toJson(t).toString()
        val body = RequestBody.create(Constants.JSON, jsonString)
        val request = Request.Builder()
                .url(usersUrl)
                .put(body)
                .build()

        client.newCall(request).execute()
    }

    fun getUserInfo(): User {
            val request = Request.Builder()
                    .url(usersUrl)
                    .get()
                    .build()

            val response = client.newCall(request).execute()
            val responseString = response.body().string()

            val user = gson.fromJson(responseString, User::class.java)
            return user
    }

    fun getImageUris(): List<String> {
        val request = Request.Builder()
                .url(imagesUrl)
                .get()
                .build()

        val response = client.newCall(request).execute()
        val items = parseResponse(response)
        return items
    }

    @Throws(IOException::class, JSONException::class)
    private fun parseResponse(response: Response): List<String> {
        val imagesUris = ArrayList<String>()
        val responseString = response.body().string()
        val rootJsonObject = JSONObject(responseString)
        val images = rootJsonObject.getJSONArray("items")
        for (i in 0 until images.length()) {
            val image = images.getJSONObject(i)
            val imageUrl = """$imagesUrl${image.get("name")}?alt=media"""
            imagesUris.add(imageUrl)
        }

        return imagesUris
    }
}