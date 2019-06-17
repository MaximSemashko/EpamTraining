package com.example.epamtraining.repositories

import com.example.epamtraining.Constants.Companion.PRODUCTS_URL
import com.example.epamtraining.contracts.ProductsContract
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseDatabase

class ProductsRepository : ProductsContract.Repository {
    override fun getProducts(): ArrayList<Products> {
        return FirebaseDatabase.getItems<Products>(PRODUCTS_URL) as ArrayList<Products>
    }
}