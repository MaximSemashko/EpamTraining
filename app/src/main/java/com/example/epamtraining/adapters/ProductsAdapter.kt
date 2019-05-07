package com.example.epamtraining.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.models.Products
import kotlinx.android.synthetic.main.product_item.view.*

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val productsList = ArrayList<Products>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.product_item, viewGroup, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ProductsAdapter.ProductsViewHolder, i: Int) {
        viewHolder.bind(productsList.get(i))
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun updateItems(pItems: List<Products>) {
        productsList.clear()
        productsList.addAll(pItems)
        notifyDataSetChanged() //TODO DiffUtils
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(products: Products) {
            itemView.productNameTextView.text = products.name
            itemView.productsCaloriesTextView.text = products.callories.toString()
        }
    }
}
