package com.example.epamtraining.repositories

import com.example.epamtraining.contracts.LoginContract
import com.example.epamtraining.models.UserLogin
import com.example.epamtraining.network.FirebaseAuth
import org.json.JSONObject

class LoginRepository : LoginContract.Repository {

    override fun parseResponse(user: UserLogin, url: String): Boolean {
        val response = FirebaseAuth.userAuth(user, url)
        if (response?.code() != 400) {
            val responseString = response?.body()?.string()
            val rootJsonObject = JSONObject(responseString)

            FirebaseAuth.localId = rootJsonObject.get("localId").toString()
            FirebaseAuth.token = rootJsonObject.get("idToken").toString()
            return true
        }

        return false
    }
}