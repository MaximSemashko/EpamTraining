package com.example.epamtraining.backend

import com.example.epamtraining.models.Products

interface IProductsWebService {

    fun loadProducts(result: ICallback<List<Products>>)

    object Utils {

        private val INSTANCE: IProductsWebService = ProductsWebService()

        fun getInstance(): IProductsWebService {
            return INSTANCE
        }
    }
}