package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.models.Products
import kotlinx.android.synthetic.main.activity_products.*
import java.util.*

class ProductsActivity : AppCompatActivity() {

    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        productsAdapter = ProductsAdapter()

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
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
