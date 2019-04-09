package com.example.epamtraining.models

import java.util.*

data class Trainings(val id: UUID, val name: String,
                     val duration: Double, val exercisesList: List<Exercises>)