package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.epamtraining.R
import com.google.gson.JsonParser
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_login.*
import java.io.IOException
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
//                                login(TestUser(email, password))
                postLogin(email, password)
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
            Log.i("TAG", rootJsonObject.toString())
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

    var client = OkHttpClient()

    @Throws(IOException::class)
    fun postLogin(email: String, password: String) {
//         var gson = Gson()
//         var jsonString = gson.toJson(TestUser(email, password))
        val jsonString = "{\"email\":\"${email}\",\"password\":\"${password}\",\"returnSecureToken\":true}"
        val body = RequestBody.create(JSON, jsonString)
        val URL = URL(BASE_URL + OPERATION_VERIFY_PASSWORD + "?key=" + firebaseKey)
        val request = Request.Builder()
                .url(URL)
                .post(body)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                Log.i("TAG", response.toString())
            }

            override fun onFailure(request: Request?, e: IOException?) {
                Log.i("TAG", request.toString())
            }

        })
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        private const val firebaseKey = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        private const val OPERATION_VERIFY_PASSWORD = "verifyPassword"
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }
}
