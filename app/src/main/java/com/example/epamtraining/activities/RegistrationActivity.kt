package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.epamtraining.R
import com.example.epamtraining.models.Users
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class RegistrationActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //TODO
        try {
            val child = JSONObject()

            child.put("name", "asd")
            child.put("age", "asd")
            child.put("sex", "Asd")
            child.put("height", "asd")
            child.put("weight", "sad")

            val parent = JSONObject()
            parent.put(token,child)

            val jsonObject = JSONObject()
            jsonObject.put("age", "23")
            jsonObject.put("address", "usa")

            val userJson = JSONObject()
            userJson.put("user", jsonObject)

            val yourFormat = "data$parent"
            Log.d("TAG", "Format: $yourFormat")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        registerButton.setOnClickListener {
                        val userNew = Users(id = token,
                    email = usersEmailEditText.text.toString(),
                    name = usersNameEditText.text.toString(),
                    password = usersPasswordEditText.text.toString(),
                    age = Integer.parseInt(usersAgeEditText.text.toString()),
                    weight = usersWeightEditText.text.toString().toDouble(),
                    height = usersHeightEditText.text.toString().toDouble())

            val user = TestUser(usersEmailEditText.text.toString(),
                    usersPasswordEditText.text.toString())
            thread {
                token = signUnWithEmailAndPassword(user)
              //  addUserToRealtimeDatabase(userNew)
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

    //TODO
    @Throws(IOException::class)
    private fun addUserToRealtimeDatabase(user: Users) {
        val obj = URL("https://ksport-8842a.firebaseio.com/users.json")
        val con = obj.openConnection() as HttpURLConnection

        con.setRequestMethod("POST")
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Authorization", "key=XXXX")

        val child = JSONObject()

        child.put("name", user.name)
        child.put("age", user.age)
        child.put("sex", user.sex)
        child.put("height", user.height)
        child.put("weight", user.weight)

        val parent = JSONObject()
        parent.put(token,child)

        con.setDoOutput(true)

        val os = OutputStreamWriter(con.outputStream)
        os.write(parent.toString())
        os.flush()
        os.close()
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        private const val firebaseKey = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        private const val OPERATION_SIGN_UP_USER = "signupNewUser"
    }
}

data class TestUser(val email: String?, val password: String?)