package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.callbacks.ItemTouchCallback
import com.example.epamtraining.dialogs.ProductsDialogFragment
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_products.*
import kotlin.concurrent.thread


class ProductsActivity : AppCompatActivity(), ProductsDialogFragment.addProductDialogListener {

    override fun addProduct(product: Products) {
        productsAdapter.addItem(product)
        FirebaseDatabase.postToRealtimeDatabase(product, url)
    }

    private lateinit var productsAdapter: ProductsAdapter
    private var products = ArrayList<Products>()
    val url = "https://ksport-8842a.firebaseio.com/Products.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        productsAdapter = ProductsAdapter()

        val itemTouchHelper = ItemTouchHelper(ItemTouchCallback(productsAdapter))

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
            itemTouchHelper.attachToRecyclerView(this)
            itemAnimator = object : DefaultItemAnimator() {
                override fun animateMove(holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
                    return super.animateMove(holder, fromX, fromY, toX, toY)
                }
            }

            addItemDecoration((DividerItemDecoration(this@ProductsActivity, DividerItemDecoration.VERTICAL)))
        }
        showProgress()
        thread {
            products = FirebaseDatabase.getProducts(url) as ArrayList<Products>

            runOnUiThread {
                productsAdapter.updateItems(products)
                hideProgress()
            }
        }

        productsFab.setOnClickListener {
            val productsDialogFragment = ProductsDialogFragment()
            productsDialogFragment.show(supportFragmentManager, productsDialogFragment.tag)
        }
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

    companion object {
        fun startProductsActivity(packageContext: Context?): Intent {
            val intent = Intent(packageContext, ProductsActivity::class.java)
            return intent
        }
    }
}