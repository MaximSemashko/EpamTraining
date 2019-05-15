package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.epamtraining.R
import com.example.epamtraining.models.Users
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors


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
                addUserToRealtimeDatabase(userNew)
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

    @Throws(IOException::class)
    private fun addUserToRealtimeDatabase(user: Users) {
        val obj = URL("https://ksport-8842a.firebaseio.com/users/$localId.json")
        val con = obj.openConnection() as HttpURLConnection
        val url = "https://ksport-8842a.firebaseio.com/users.json"
        con.setRequestMethod("PUT")
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Authorization", "key=XXXX")
        val dataMessages = JSONObject()

        dataMessages.put("name", user.name.toString())
        dataMessages.put("age", user.age.toString())
        dataMessages.put("sex", user.sex.toString())
        dataMessages.put("height", user.height.toString())
        dataMessages.put("weight", user.weight.toString())

        con.setDoOutput(true)

        val os = OutputStreamWriter(con.outputStream)
        os.write(dataMessages.toString())
        os.flush()
        os.close()
        val responseCode = con.getResponseCode()
        println("\nSending 'POST' request to URL : $url")
        println("Post parameters : $dataMessages")
        println("Response Code : " + responseCode + " " + con.getResponseMessage())
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