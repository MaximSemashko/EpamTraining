package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.backend.IProductsWebService
import com.example.epamtraining.models.Products
import kotlinx.android.synthetic.main.activity_products.*
import java.net.URL
import java.util.*
import kotlin.concurrent.thread


const val url = "https://ksport-8842a.firebaseio.com/Products.json"

class ProductsActivity : AppCompatActivity() {

    private lateinit var productsAdapter: ProductsAdapter
    private val ProductsWebService = IProductsWebService.Utils.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        productsAdapter = ProductsAdapter()

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }


        thread {
            val repoListJsonStr = URL(url).readText()
            Log.d("JSON", repoListJsonStr)
        }

        productsAdapter.setItems(getProducts())

    }

    fun getProducts(): Collection<Products> {
        return Arrays.asList(
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0),
                Products(UUID.randomUUID().toString(), "Some", 35.0))
    }
}
