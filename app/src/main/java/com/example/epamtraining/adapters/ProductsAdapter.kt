package com.example.epamtraining.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.interfaces.ItemTouchHelperAdapter
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseAuth.localId
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.product_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class ProductsAdapter(context: Context) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(), ItemTouchHelperAdapter {

    private val productsList = ArrayList<Products>()
    private val url = "https://ksport-8842a.firebaseio.com/users/$localId/Breakfast.json"
    private val layoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductsViewHolder {
        return ProductsViewHolder(layoutInflater.inflate(R.layout.product_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ProductsViewHolder, i: Int) {
        viewHolder.bind(productsList[i])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun updateItems(products: List<Products>) {
        this.productsList.clear()
        this.productsList.addAll(products)
        notifyDataSetChanged()
    }

    fun addItem(product: Products) {
        productsList.add(product)
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(productsList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(productsList, i, i - 1)
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }

    override fun addUserBreakfast(position: Int) {
        val product = productsList.get(position)
        FirebaseDatabase.postToRealtimeDatabase(product, url = url)
        productsList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(products: Products) {
            itemView.productNameTextView.text = products.name
            itemView.productsCaloriesTextView.text = products.calories.toString()
        }
    }
}
