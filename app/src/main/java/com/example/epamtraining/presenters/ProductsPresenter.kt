package com.example.epamtraining.presenters

import com.example.epamtraining.contracts.ProductsContract
import kotlin.concurrent.thread

class ProductsPresenter(val view: ProductsContract.View, val repository: ProductsContract.Repository) : ProductsContract.Presenter {

    override fun initList() {
            view.showProgress()
            thread {
                view.updateList(repository.getProducts())
            }
    }

    override fun onFabWasClicked() {
        view.startDialog()
    }
}