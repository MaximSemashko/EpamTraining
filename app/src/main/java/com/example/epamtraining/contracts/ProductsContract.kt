package com.example.epamtraining.contracts

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
        fun updateList(products: ArrayList<Products>)
    }

    interface Presenter {
        fun onFabWasClicked()
        fun initList()
    }
}