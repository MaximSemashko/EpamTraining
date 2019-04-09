package com.example.epamtraining.models

import java.util.*

data class Users(val id: UUID, val email: String,
                 val password: String, val name: String,
                 val sex: String, val age: Int,
                 val weight: Int, val height: Double,
                 val trainings: List<Trainings>)