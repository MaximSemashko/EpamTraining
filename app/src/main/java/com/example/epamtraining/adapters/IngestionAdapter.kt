package com.example.epamtraining.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.epamtraining.R
import com.example.epamtraining.models.Products
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.product_item.view.*

/*TODO
* 1) remove method
* 2) fix incorrect behavior of viewholder items
* 3) clean project
* 4) remove unneccessary code (if i dont do this)
* 5) How to get url of product? Maybe i can take id?
* 6) Go to kotlin extensions!!!
* TODO*/

class IngestionAdapter(context: Context?) : RecyclerView.Adapter<IngestionAdapter.ViewHolder>() {

    private val productsList = ArrayList<Products>()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(productsList[i])

        viewHolder.deleteProductButton.setOnClickListener {
            Log.i("TAG", "Will be removed at ${viewHolder.adapterPosition}")
            removeMeal(viewHolder.adapterPosition)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.product_item, viewGroup, false))
    }

    fun updateItems(products: List<Products>) {
        this.productsList.clear()
        this.productsList.addAll(products)
        notifyDataSetChanged()
    }

    fun removeMeal(position: Int) {
        FirebaseDatabase.removeFromRealtimeDatabase("https://ksport-8842a.firebaseio.com/users/Dn9GGcpNJMbGVFNpAeuZE3gpXG23/Breakfast/-LfuRZtpI5xaBCY86h0L.json")
        productsList.removeAt(position)
        notifyItemRemoved(position)
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
        var deleteProductButton: Button = itemView.findViewById(R.id.deleteProductButton)

        fun bind(products: Products) {
            itemView.workoutNameTextView.text = products.name
            itemView.numberOfRepeatsTextView.text = products.calories.toString()
        }

        init {
            itemView.setOnLongClickListener {
                Log.i("TAG", "Pos $adapterPosition")
                itemView.deleteProductButton.visibility = View.VISIBLE
                true
            }
        }
    }
}