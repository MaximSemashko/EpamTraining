package com.example.epamtraining.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.activities.ProductsActivity
import com.example.epamtraining.adapters.IngestionAdapter
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_ingestion.*
import kotlin.concurrent.thread

class IngestionFragment : Fragment() {

    private var products: List<Products> = ArrayList()
    private lateinit var url: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_ingestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ingestionAdapter = IngestionAdapter(activity)

        ingestionRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ingestionAdapter

        }

        showProgress()
        thread {
            products = FirebaseDatabase.getProducts(url)

            activity?.runOnUiThread {
                ingestionAdapter.updateItems(products)
                totalCaloriesTextView.setText(ingestionAdapter.getMealCalories().toString())
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
                        ingestionAdapter.updateItems(products)
                        totalCaloriesTextView.setText(ingestionAdapter.getMealCalories().toString())
                        isRefreshing = false
                    }
                }
                activity?.runOnUiThread {
                    ingestionAdapter.updateItems(products)

                }
            }

            addProductFab.setOnClickListener {
                ProductsActivity.startProductsActivity(context, url)
            }
        }
    }

    fun setUrl(url: String) {
        this.url = url
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
