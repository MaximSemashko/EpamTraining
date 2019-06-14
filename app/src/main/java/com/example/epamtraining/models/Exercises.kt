package com.example.epamtraining.models

import com.google.gson.annotations.SerializedName

class Exercises(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("repeats")
        val repeats: Int?,
        @SerializedName("calories")
        var calories: Double?)