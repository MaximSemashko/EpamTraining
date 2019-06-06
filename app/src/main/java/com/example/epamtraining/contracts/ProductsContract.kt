package com.example.epamtraining.contracts

import android.os.Bundle
import com.example.epamtraining.models.Products

interface ProductsContract {

    interface Repository {
        fun getProducts(): ArrayList<Products>
    }

    interface View {
        fun initRecycler()
        fun startDialog()
        fun showProgress()
        fun hideProgress()
        fun onSaveInstanceState(outState: Bundle?)
        fun updateList(products: ArrayList<Products>)
    }

    interface Presenter {
        fun onFabWasClicked()
        fun checkSavedInstanceState(savedInstanceState: Bundle?, key: String)
    }
}