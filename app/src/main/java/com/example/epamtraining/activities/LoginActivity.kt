package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.epamtraining.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign_in_button.setOnClickListener {
            val email: String = user_email_text.text.toString()
            val password: String = user_password_text.text.toString()
            Toast
                    .makeText(this,"email: $email '\n' password: $password",Toast.LENGTH_SHORT)
                    .show()
        }
    }
}
