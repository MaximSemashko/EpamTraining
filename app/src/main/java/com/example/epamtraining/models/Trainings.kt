package com.example.epamtraining.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Trainings(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("duration")
        val duration: String?,
        var exercisesList: ArrayList<Exercises>?
)