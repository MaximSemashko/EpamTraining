package com.example.epamtraining.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exercises(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("repeats")
        val repeats: Int?,
        @SerializedName("calories")
        var calories: Double?) : Parcelable