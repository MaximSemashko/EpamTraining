package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.backend.IProductsWebService
import com.example.epamtraining.models.Products
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_products.*
import java.io.IOException


class ProductsActivity : AppCompatActivity() {

    private lateinit var productsAdapter: ProductsAdapter
    private val ProductsWebService = IProductsWebService.Utils.getInstance()
    val url = "https://ksport-8842a.firebaseio.com/Products.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        productsAdapter = ProductsAdapter()

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }

        fetchJson()
    }

    private fun fetchJson() {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                val responseBody = response?.body()?.string()
                Log.i("TAG",responseBody)
                val gson = GsonBuilder().create()
                val products = gson.fromJson(responseBody, Products::class.java)
                Log.i("TAG",products.toString())
                runOnUiThread{
                    productsAdapter.updateItems(listOf(products))
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
            }
        })
    }

    /* private fun fetchJson() {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                val responseBody = response?.body()?.string()
                val gson = GsonBuilder().create()
                val objects = JSONObject(responseBody)
                val iterator = objects.keys()
                while (iterator.hasNext()) {
                    val obj = objects.getJSONObject(iterator.next())
                    val prod =gson.from
                            runOnUiThread {
                                productsAdapter.updateItems(obj)

                            }
                    Log.d("TAG", obj.toString())
                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }*/
}