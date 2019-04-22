package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.epamtraining.R
import com.example.epamtraining.models.Users
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*


class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        registerButton.setOnClickListener {
            val user = Users(id = UUID.randomUUID(),
                    email = usersEmailEditText.text.toString(),
                    name = usersNameEditText.text.toString(),
                    password = usersPasswordEditText.text.toString(),
                    age = Integer.parseInt(usersAgeEditText.text.toString()),
                    weight = usersWeightEditText.text.toString().toDouble(),
                    height = usersHeightEditText.text.toString().toDouble())

            Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
