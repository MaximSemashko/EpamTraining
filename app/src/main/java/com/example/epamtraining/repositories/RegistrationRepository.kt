package com.example.epamtraining.repositories

import com.example.epamtraining.Constants
import com.example.epamtraining.contracts.RegistrationContract
import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseDatabase
import org.json.JSONObject

class RegistrationRepository : RegistrationContract.Repository {

    private val url: String = Constants.BASE_URL + Constants.OPERATION_SIGN_UP_USER + "?key=" + Constants.firebaseKey

    override fun parseResponse(user:User,url:String):Boolean {
        val response = FirebaseAuth.userAuth(user, url)
        if (response!!.code() != 400) {
            val responseString = response.body()?.string()
            val rootJsonObject = JSONObject(responseString)

            FirebaseAuth.localId = rootJsonObject.get("localId").toString()
            FirebaseAuth.token = rootJsonObject.get("idToken").toString()

            FirebaseDatabase.putToRealtimeDatabase(user)
            return true
        }

        return false
    }
}