package com.example.epamtraining.repositories

import com.example.epamtraining.contracts.MainContract
import com.example.epamtraining.network.FirebaseAuth

class MainRepository : MainContract.Repository {
    override fun isUserOnline(): Boolean {
        return FirebaseAuth.token == null
    }
}