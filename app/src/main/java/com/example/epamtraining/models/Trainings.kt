package com.example.epamtraining.models

import java.util.*

data class Trainings(val id: UUID, val name: String,
                     val sets: Int, val exercisesList: List<Exercises>)