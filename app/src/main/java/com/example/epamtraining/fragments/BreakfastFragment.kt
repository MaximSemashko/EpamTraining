package com.example.epamtraining.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.activities.ProductsActivity.Companion.startProductsActivity
import com.example.epamtraining.adapters.BreakfastAdapter
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_ingestion.*
import kotlin.concurrent.thread

class BreakfastFragment : Fragment() {

    private var products: List<Products> = ArrayList()
    private val url = "https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Breakfast.json"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_ingestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsAdapter = BreakfastAdapter(activity)

        ingestionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter

        }
        showProgress()
        thread {
            products = FirebaseDatabase.getProducts(url)

            activity?.runOnUiThread {
                productsAdapter.updateItems(products)
                hideProgress()
            }
        }

        ingestionSwipeRefreshLayout.apply {
            setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

            setOnRefreshListener {
                thread {
                    products = FirebaseDatabase.getProducts(url)

                    activity?.runOnUiThread {
                        productsAdapter.updateItems(products)
                        isRefreshing = false
                    }
                }
                activity?.runOnUiThread {
                    productsAdapter.updateItems(products)

                }
            }

            addProductFab.setOnClickListener {
                startProductsActivity(context)
            }
        }
    }

    fun hideProgress() {
        if (breakfastProgressBar.visibility != View.GONE) {
            breakfastProgressBar.visibility = View.GONE
        }
    }

    fun showProgress() {
        if (breakfastProgressBar.visibility != View.VISIBLE) {
            breakfastProgressBar.visibility = View.VISIBLE
        }
    }
}
