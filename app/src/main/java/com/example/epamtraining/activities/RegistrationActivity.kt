package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.epamtraining.R
import com.example.epamtraining.models.Users
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap


class RegistrationActivity : AppCompatActivity() {

    private var token: String? = null
    private var localId: String? = null
    private val executor = Executors.newCachedThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerButton.setOnClickListener {

            val user = TestUser(usersEmailEditText.text.toString(),
                    usersPasswordEditText.text.toString())

            executor.execute {
                token = signUnWithEmailAndPassword(user)
                println(token)
                val userNew = Users(id = UUID.randomUUID().toString(),
                        email = usersEmailEditText.text.toString(),
                        name = usersNameEditText.text.toString(),
                        password = usersPasswordEditText.text.toString(),
                        age = Integer.parseInt(usersAgeEditText.text.toString()),
                        weight = usersWeightEditText.text.toString().toDouble(),
                        height = usersHeightEditText.text.toString().toDouble())
                Log.i("TAG", "User: $userNew")
                localId = setAccountInfo(user)
                putUserToRealtimeDatabase(userNew)
            }

        }
    }

    @Throws(Exception::class)
    fun signUnWithEmailAndPassword(user: TestUser): String? {
        var urlRequest: HttpURLConnection? = null
        var token: String?

        try {
            val URL = URL(BASE_URL + OPERATION_SIGN_UP_USER + "?key=" + firebaseKey)
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

            token = rootJsonObject.get("idToken").asString
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

    var client = OkHttpClient()

    @Throws(IOException::class)
    fun putUserToRealtimeDatabase(user:Users) {
        var gson = Gson()

        val userJson = HashMap<String,Any?>()
        userJson["id"] = user.id
        userJson["email"] = user.email
        userJson["password"] = user.password
        userJson["sex"] = user.sex
        userJson["weight"] = user.weight
        userJson["height"] = user.height

        var jsonString = gson.toJson(userJson).toString()

        val body = RequestBody.create(LoginActivity.JSON, jsonString)
        val url = "https://ksport-8842a.firebaseio.com/users/$localId.json"
        val request = Request.Builder()
                .url(url)
                .put(body)
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

    @Throws(Exception::class)
    fun setAccountInfo(user: TestUser): String? {
        var urlRequest: HttpURLConnection? = null
        try {
            val URL = URL(BASE_URL + OPERATION_SET_ACCOUNT_INFO + "?key=" + firebaseKey)
            urlRequest = URL.openConnection() as HttpURLConnection
            urlRequest.doOutput = true
            urlRequest.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            val outputStream = urlRequest.outputStream
            val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
            outputStreamWriter.write("{\"idToken\":\"$token\",\"email\":\"${user.email}\",\"password\":\"${user.password}\",\"returnSecureToken\":true}")
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
        private const val OPERATION_SIGN_UP_USER = "signupNewUser"
        private const val OPERATION_SET_ACCOUNT_INFO = "setAccountInfo"
    }
}

data class TestUser(val email: String?, val password: String?)