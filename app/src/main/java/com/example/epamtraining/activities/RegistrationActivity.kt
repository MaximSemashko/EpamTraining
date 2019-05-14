package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        registerButton.setOnClickListener {
            //            val user = Users(id = UUID.randomUUID(),
//                    email = usersEmailEditText.text.toString(),
//                    name = usersNameEditText.text.toString(),
//                    password = usersPasswordEditText.text.toString(),
//                    age = Integer.parseInt(usersAgeEditText.text.toString()),
//                    weight = usersWeightEditText.text.toString().toDouble(),
//                    height = usersHeightEditText.text.toString().toDouble())

            val user = TestUser(usersEmailEditText.text.toString(),
                    usersPasswordEditText.text.toString())
            thread {
                signUpNewUser(user)
            }
        }
    }

    @Throws(Exception::class)
    fun signUpNewUser(user: TestUser): String? {

        var urlRequest: HttpURLConnection? = null
        var token: String?

        try {
            val URL = URL(BASE_URL + OPERATION_SIGN_UP_USER + "?key=" + firebaseKey)
            urlRequest = URL.openConnection() as HttpURLConnection
            urlRequest.doOutput = true
            urlRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            val os = urlRequest.outputStream
            val osw = OutputStreamWriter(os, "UTF-8")
            osw.write("{\"email\":\"${user.email}\",\"password\":\"${user.password}\",\"returnSecureToken\":true}")
            osw.flush()
            osw.close()

            urlRequest.connect()

            val jp = JsonParser() //from gson
            val root = jp.parse(InputStreamReader(urlRequest.content as InputStream)) //Convert the input stream to a json element
            val rootobj = root.asJsonObject //May be an array, may be an object.

            token = rootobj.get("idToken").asString
            println("User token $token")
            println("\nSending 'POST' request to URL : $URL")
            println("Post parameters : $parent")
            println("Response Code : " + urlRequest.getResponseMessage())
        } catch (e: Exception) {
            return null
        } finally {
            urlRequest?.disconnect()
        }

        return token
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        private const val firebaseKey = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        private const val OPERATION_SIGN_UP_USER= "signupNewUser"
    }
}

data class TestUser(val email: String?, val password: String?)