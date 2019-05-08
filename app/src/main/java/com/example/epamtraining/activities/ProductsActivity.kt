package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.models.Products
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_products.*
import org.json.JSONObject
import java.io.IOException


class ProductsActivity : AppCompatActivity() {

    private lateinit var productsAdapter: ProductsAdapter
    private val products = ArrayList<Products>()
    val url = "https://ksport-8842a.firebaseio.com/Products.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.epamtraining.R.layout.activity_products)
        productsAdapter = ProductsAdapter()

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
        fetchJson()
    }

    private fun fetchJson() {
        showProgress()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                val responseBody = response?.body()?.string()
                val objects = JSONObject(responseBody)
                val iterator = objects.keys()
                while (iterator.hasNext()) {
                    val obj = objects.getJSONObject(iterator.next())
                    val product = Products(obj.getString("id"),
                            obj.getString("name"),
                            obj.getDouble("callories"))

                    products.add(product)
                }
                runOnUiThread {
                    productsAdapter.updateItems(products)
                    hideProgress()
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                hideProgress()
            }
        })
    }

    private fun hideProgress() {
        if (progressBar.visibility !== View.GONE) {
            progressBar.setVisibility(View.GONE)
        }
    }

    private fun showProgress() {
        if (progressBar.visibility !== View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE)
        }
    }
}