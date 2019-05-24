package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.epamtraining.R
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post.*
import org.json.JSONObject
import kotlin.concurrent.thread


class PostActivity : AppCompatActivity() {

    private val gson = Gson()
    private val client = OkHttpClient()
    private val url = "https://firebasestorage.googleapis.com/v0/b/ksport-8842a.appspot.com/o/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        loadImage.setOnClickListener {
            thread {
                getImage()
            }
        }
    }

    private fun getImage() {
        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()
        val rootJsonObject = JSONObject(responseString)
        val images = rootJsonObject.getJSONArray("items")
        Log.i("TAG", "Images $images")
        for (i in 0 until images.length()) {
            val image = images.getJSONObject(i)
            val imageUrl = """$url${image.get("name")}?alt=media"""
            Log.i("TAG","Image: $image")
            Log.i("TAG", "Image url: $imageUrl")
            runOnUiThread {
                Picasso.with(this)
                        .load(imageUrl)
                        .into(testImageView)
            }
        }
        Log.i("TAG", responseString)
    }
}

