package com.example.epamtraining.models

import com.google.gson.annotations.SerializedName

data class Users(
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
        var age: Int? = 0,
        @SerializedName("weight")
        var weight: Double? = 0.0,
        @SerializedName("height")
        var height: Double? = 0.0
)