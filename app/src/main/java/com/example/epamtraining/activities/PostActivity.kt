package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.epamtraining.R
import com.example.epamtraining.network.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post.*
import kotlin.concurrent.thread


class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        loadImage.setOnClickListener {
            thread {
                val image = FirebaseDatabase.getImage()
                Log.i("TAG","URL $image")
                runOnUiThread{
                    Picasso.with(this)
                            .load(image)
                            .into(testImageView)
                }
            }
        }
    }
}

