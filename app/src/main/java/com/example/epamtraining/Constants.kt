package com.example.epamtraining

import com.squareup.okhttp.MediaType

class Constants {
    companion object {
        const val BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/"
        const val FIREBASE_KEY = "AIzaSyBgd6Go-zLI3qar4aKm4uCyEAjIhCbMlxQ"
        const val OPERATION_VERIFY_PASSWORD = "verifyPassword"
        const val OPERATION_SIGN_UP_USER = "signupNewUser"

        const val STORAGE_URL = "https://firebasestorage.googleapis.com/v0/b/ksport-8842a.appspot.com/o/"

        const val PRODUCTS_URL = "https://ksport-8842a.firebaseio.com/Products.json"

        val JSON = MediaType.parse("application/json; charset=utf-8")
    }
}