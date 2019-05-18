package com.example.epamtraining.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.dialogs.ProductsDialogFragment
import com.example.epamtraining.models.Products
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_products.*
import org.json.JSONObject
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class ProductsActivity : AppCompatActivity(), ProductsDialogFragment.addProductDialogListener {

    override fun addProduct(product: Products) {
        productsAdapter.addItem(product)
        thread {
            sendMsg(product)
        }
    }

    private lateinit var productsAdapter: ProductsAdapter
    private val products = ArrayList<Products>()
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

        productsFab.setOnClickListener {
            val productsDialogFragment = ProductsDialogFragment()
            productsDialogFragment.show(supportFragmentManager, productsDialogFragment.tag)
        }
    }

    private fun fetchJson() {
        showProgress()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(response: Response?) {
                val responseBody = response?.body()?.string()
                Log.i("TAG",responseBody)
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

    @Throws(IOException::class)
    private fun sendMsg(product: Products) {
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection

        con.setRequestMethod("POST")
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Authorization", "key=XXXX")


        val parent = JSONObject()
        parent.put("callories", product.calories)
        parent.put("id", product.id)
        parent.put("name", product.name)

        con.setDoOutput(true)

        val os = OutputStreamWriter(con.outputStream)
        os.write(parent.toString())
        os.flush()
        os.close()
        val responseCode = con.getResponseCode()
        println("\nSending 'POST' request to URL : $url")
        println("Post parameters : $parent")
        println("Response Code : " + responseCode + " " + con.getResponseMessage())

    }

    private fun hideProgress() {
        if (progressBar.visibility != View.GONE) {
            progressBar.visibility = View.GONE
        }
    }

    private fun showProgress() {
        if (progressBar.visibility != View.VISIBLE) {
            progressBar.visibility = View.VISIBLE
        }
    }
}