package com.example.epamtraining.models

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id")
        val id: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("sex")
        val sex: String? = "sex",
        @SerializedName("age")
        val age: Int?,
        @SerializedName("weight")
        val weight: Double?,
        @SerializedName("height")
        val height: Double?
)