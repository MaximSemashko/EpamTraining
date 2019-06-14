package com.example.epamtraining.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epamtraining.Constants.Companion.PRODUCTS_URL
import com.example.epamtraining.R
import com.example.epamtraining.adapters.ProductsAdapter
import com.example.epamtraining.callbacks.ProductTouchCallback
import com.example.epamtraining.contracts.ProductsContract
import com.example.epamtraining.dialogs.ProductsDialogFragment
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseDatabase
import com.example.epamtraining.presenters.ProductsPresenter
import com.example.epamtraining.repositories.ProductsRepository
import kotlinx.android.synthetic.main.activity_products.*


class ProductsActivity : AppCompatActivity(), ProductsDialogFragment.AddProductDialogListener, ProductsContract.View {

    private lateinit var url: String
    private lateinit var productsAdapter: ProductsAdapter
    private val productsRepository = ProductsRepository()
    private val productsPresenter = ProductsPresenter(this, productsRepository)

    override fun addProduct(product: Products) {
        productsAdapter.addItem(product)
        FirebaseDatabase.postToRealtimeDatabase(product, PRODUCTS_URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        getUrl()

        initRecycler()

        showProgress()

        productsPresenter.initList()

        productsFab.setOnClickListener {
            productsPresenter.onFabWasClicked()
        }
    }

    override fun getUrl() {
        url = intent.getStringExtra(NUTRITION_URL)
    }

    override fun startDialog() {
        val productsDialogFragment = ProductsDialogFragment()
        productsDialogFragment.show(supportFragmentManager, productsDialogFragment.tag)
    }

    override fun updateList(products: ArrayList<Products>) {
        runOnUiThread {
            productsAdapter.updateItems(products)
            hideProgress()
        }
    }

    override fun initRecycler() {
        productsAdapter = ProductsAdapter(this@ProductsActivity, url)
        val itemTouchHelper = ItemTouchHelper(ProductTouchCallback(productsAdapter))

        productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
            itemTouchHelper.attachToRecyclerView(this)

            addItemDecoration((androidx.recyclerview.widget.DividerItemDecoration(this@ProductsActivity, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL)))
        }
    }

    override fun hideProgress() {
        if (progressBar.visibility != View.GONE) {
            progressBar.visibility = View.GONE
        }
    }

    override fun showProgress() {
        if (progressBar.visibility != View.VISIBLE) {
            progressBar.visibility = View.VISIBLE
        }
    }

    companion object {
        fun startProductsActivity(packageContext: Context?, url: String) {
            val intent = Intent(packageContext, ProductsActivity::class.java)
            intent.putExtra(NUTRITION_URL, url)
            packageContext?.startActivity(intent)
        }

        private const val NUTRITION_URL = "key"
    }
}