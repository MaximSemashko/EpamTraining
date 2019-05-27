package com.example.epamtraining.network

import com.example.epamtraining.Constants
import com.example.epamtraining.Constants.Companion.STORAGE_URL
import com.example.epamtraining.models.Products
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
import kotlin.concurrent.thread


object FirebaseDatabase {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val usersUrl = "https://ksport-8842a.firebaseio.com/users/$localId.json"
    val products = ArrayList<Products>()

    @Throws(IOException::class)
    fun <T> putToRealtimeDatabase(t: T) {
        val jsonString = gson.toJson(t).toString()
        val body = RequestBody.create(Constants.JSON, jsonString)
        val request = Request.Builder()
                .url(usersUrl)
                .put(body)
                .build()

        client.newCall(request).execute()
    }

    @Throws(IOException::class)
    fun <T> postToRealtimeDatabase(t: T, url: String) {
        thread {
            val jsonString = gson.toJson(t).toString()
            val body = RequestBody.create(Constants.JSON, jsonString)
            val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

            client.newCall(request).execute()
        }
    }

    fun getProducts(url: String): List<Products> {
        val request = Request
                .Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response?.body()?.string()

        //wtf
        if (responseBody.equals("null")) {
            return emptyList()
        } else {
            val objects = JSONObject(responseBody)
            val iterator = objects.keys()
            while (iterator.hasNext()) {
                val obj = objects.getJSONObject(iterator?.next())
                val product = gson.fromJson(obj.toString(), Products::class.java)
                products.add(product)
            }

            return products
        }
    }

    fun getUserInfo(): User {
        val request = Request.Builder()
                .url(usersUrl)
                .get()
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return gson.fromJson(responseString, User::class.java)
    }

    fun getImageUris(): List<String> {
        val request = Request.Builder()
                .url(STORAGE_URL)
                .get()
                .build()

        val response = client.newCall(request).execute()
        return parseImageUrisResponse(response)
    }

    @Throws(IOException::class, JSONException::class)
    private fun parseImageUrisResponse(response: Response): List<String> {
        val imagesUris = ArrayList<String>()

        val responseString = response.body().string()

        val rootJsonObject = JSONObject(responseString)

        val images = rootJsonObject.getJSONArray("items")
        for (i in 0 until images.length()) {
            val image = images.getJSONObject(i)
            val imageUrl = """$STORAGE_URL${image.get("name")}?alt=media"""

            imagesUris.add(imageUrl)
        }

        return imagesUris
    }
}