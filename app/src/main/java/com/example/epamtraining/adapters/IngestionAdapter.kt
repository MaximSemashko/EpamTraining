package com.example.epamtraining.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.models.Products
import kotlinx.android.synthetic.main.product_item.view.*


class IngestionAdapter(context: Context?) : RecyclerView.Adapter<IngestionAdapter.ViewHolder>() {

    private val productsList = ArrayList<Products>()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(productsList[i])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.product_item, viewGroup, false))
    }

    fun updateItems(products: List<Products>) {
        this.productsList.clear()
        this.productsList.addAll(products)
        notifyDataSetChanged()
    }

    fun getMealCalories(): Double? {
        var totalCalories: Double? = 0.0

        if (totalCalories != null) {
            for (product in productsList) {
                totalCalories += product.calories
            }
        }

        return totalCalories
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(products: Products) {
            itemView.productNameTextView.text = products.name
            itemView.productsCaloriesTextView.text = products.calories.toString()
        }
    }
}