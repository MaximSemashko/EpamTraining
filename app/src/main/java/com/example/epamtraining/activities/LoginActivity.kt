package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.epamtraining.R
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {


    private var localId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton.setOnClickListener {
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()
            thread {
                login(TestUser(email, password))
            }
        }
    }
    @Throws(Exception::class)
    fun login(user: TestUser): String? {
        var urlRequest: HttpURLConnection? = null
        try {
            val URL = URL(BASE_URL + OPERATION_VERIFY_PASSWORD + "?key=" + firebaseKey)
            urlRequest = URL.openConnection() as HttpURLConnection
            urlRequest.doOutput = true
            urlRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            val outputStream = urlRequest.outputStream
            val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
            outputStreamWriter.write("{\"email\":\"${user.email}\",\"password\":\"${user.password}\",\"returnSecureToken\":true}")
            outputStreamWriter.flush()
            outputStreamWriter.close()

            urlRequest.connect()

            val jsonParser = JsonParser() //from gson
            val root = jsonParser.parse(InputStreamReader(urlRequest.content as InputStream)) //Convert the input stream to a json element
            val rootJsonObject = root.asJsonObject //May be an array, may be an object.

            localId = rootJsonObject.get("localId").asString
            println("User localId $localId")
            println("\nSending 'POST' request to URL : $URL")
            println("Post parameters : $parent")
            println("Response Code : " + urlRequest.getResponseMessage())
        } catch (e: Exception) {
            return null
        } finally {
            urlRequest?.disconnect()
        }

        return localId
    }
    companion object {
        private const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        private const val firebaseKey = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        private const val OPERATION_VERIFY_PASSWORD = "verifyPassword"
    }
}
