package com.example.epamtraining.network

import android.util.Log
import com.example.epamtraining.Constants
import com.example.epamtraining.Constants.Companion.STORAGE_URL
import com.example.epamtraining.models.Exercises
import com.example.epamtraining.models.Trainings
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
    val client = OkHttpClient()
    val gson = Gson()

    private val usersUrl = "https://ksport-8842a.firebaseio.com/users/$localId.json"
    private val trainingsUrl = "https://ksport-8842a.firebaseio.com/Trainings.json"

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

    fun removeFromRealtimeDatabase(url: String) {
        thread {
            val request = Request.Builder()
                    .url(url)
                    .delete()
                    .build()

            client.newCall(request).execute()
        }
    }

    inline fun <reified T> getItems(url: String): List<T> {
        val items = ArrayList<T>()

        val request = Request
                .Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response?.body()?.string()

        return if (responseBody.equals("null")) {
            emptyList()
        } else {
            val objects = JSONObject(responseBody)
            val iterator = objects.keys()
            while (iterator.hasNext()) {
                val obj = objects.getJSONObject(iterator?.next())
                Log.i("TAG", obj.toString())

                val product = gson.fromJson(obj.toString(), T::class.java)
                items.add(product)
            }

            items
        }
    }

    fun getTrainings(): List<Trainings> {
        val trainings = ArrayList<Trainings>()

        val request = Request
                .Builder()
                .url(trainingsUrl)
                .build()

        val response = client.newCall(request).execute()
        val responseBody = response?.body()?.string()

        return if (responseBody.equals("null")) {
            emptyList()
        } else {
            val objects = JSONObject(responseBody)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val key = iterator?.next()
                val obj = objects.getJSONObject(key)

                val exercisesUrl = "https://ksport-8842a.firebaseio.com/Trainings/$key/exercises.json"

                val training = gson.fromJson(obj.toString(), Trainings::class.java)
                training.exercisesList = getItems<Exercises>(exercisesUrl) as ArrayList<Exercises>
                Log.i("TAG", training.toString())
                trainings.add(training)
                Log.i("TAG", exercisesUrl)

            }

            trainings
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