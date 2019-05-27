package com.example.epamtraining.models

import com.google.gson.annotations.SerializedName

data class Products(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("calories")
        val calories: Double)
