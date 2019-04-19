package com.example.epamtraining.models

import java.util.*

data class Users(val id: UUID,
                 val email: String,
                 val password: String,
                 val name: String,
                 var photo: String = "",
                 val sex: String = "male",
                 var age: Int = 0,
                 var weight: Double = 0.0,
                 var height: Double = 0.0
                 )