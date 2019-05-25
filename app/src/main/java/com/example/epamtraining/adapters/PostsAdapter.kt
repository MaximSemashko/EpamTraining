package com.example.epamtraining.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.epamtraining.R
import com.example.epamtraining.imageLoader.ImageLoader
import com.example.epamtraining.imageLoader.MemoryCache
import java.util.*

class PostsAdapter(private val context: Context?, private val items: ArrayList<String>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.post_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        ImageLoader.apply {
            setCache(MemoryCache())
            displayImage(items[i], viewHolder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById(R.id.postImageView) as ImageView
    }
}