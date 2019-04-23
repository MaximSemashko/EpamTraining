package com.example.epamtraining.backend

interface ICallback<T> {

    fun onResult(pResult: T)

    fun onError(throwable: Throwable)

}
