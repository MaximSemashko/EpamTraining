package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.epamtraining.Constants.Companion.PRODUCTS_URL
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.callbacks.ItemTouchCallback
import com.example.epamtraining.dialogs.ProductsDialogFragment
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_products.*
import kotlin.concurrent.thread


class ProductsActivity : AppCompatActivity(), ProductsDialogFragment.AddProductDialogListener {

    private val PRODUCTS_KEY = "key"

    override fun addProduct(product: Products) {
        productsAdapter.addItem(product)
        FirebaseDatabase.postToRealtimeDatabase(product, PRODUCTS_URL)
    }

    private lateinit var productsAdapter: ProductsAdapter

    private var products = ArrayList<Products>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        productsAdapter = ProductsAdapter(this@ProductsActivity)
        val itemTouchHelper = ItemTouchHelper(ItemTouchCallback(productsAdapter))

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
            itemTouchHelper.attachToRecyclerView(this)

            addItemDecoration((DividerItemDecoration(this@ProductsActivity, DividerItemDecoration.VERTICAL)))
        }

        if (savedInstanceState == null) {
            showProgress()
            thread {
                products = FirebaseDatabase.getProducts(PRODUCTS_URL) as ArrayList<Products>

                runOnUiThread {
                    productsAdapter.updateItems(products)
                    hideProgress()
                }
            }
        } else {
            products = savedInstanceState.getParcelableArrayList(PRODUCTS_KEY)
            productsAdapter.updateItems(products)
            hideProgress()
        }

        productsFab.setOnClickListener {
            val productsDialogFragment = ProductsDialogFragment()
            productsDialogFragment.show(supportFragmentManager, productsDialogFragment.tag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(PRODUCTS_KEY, products)

        super.onSaveInstanceState(outState)
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
        fun startProductsActivity(packageContext: Context?) {
            val intent = Intent(packageContext, ProductsActivity::class.java)
            packageContext?.startActivity(intent)
        }
    }
}