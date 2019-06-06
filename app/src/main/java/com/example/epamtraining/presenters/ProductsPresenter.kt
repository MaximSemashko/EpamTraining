package com.example.epamtraining.presenters

import android.os.Bundle
import com.example.epamtraining.contracts.ProductsContract
import kotlin.concurrent.thread

class ProductsPresenter(val view: ProductsContract.View, val repository: ProductsContract.Repository) : ProductsContract.Presenter {

    override fun checkSavedInstanceState(savedInstanceState: Bundle?, key: String) {
        if (savedInstanceState == null) {
            view.showProgress()
            thread {
                view.updateList(repository.getProducts())
            }
        } else {
            view.updateList(products = savedInstanceState.getParcelableArrayList(key))
        }
    }

    override fun onFabWasClicked() {
        view.startDialog()
    }
}