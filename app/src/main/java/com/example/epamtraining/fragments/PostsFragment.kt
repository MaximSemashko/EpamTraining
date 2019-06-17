package com.example.epamtraining.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epamtraining.R
import com.example.epamtraining.adapters.PostsAdapter
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_posts.*
import java.util.*
import kotlin.concurrent.thread

class PostsFragment : androidx.fragment.app.Fragment() {

    lateinit private var postsAdapter: PostsAdapter
    private lateinit var imageList: ArrayList<String>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thread {
            imageList = FirebaseDatabase.getImageUris() as ArrayList<String>
            activity?.runOnUiThread {
                postsAdapter = PostsAdapter(context, imageList)
                postsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = postsAdapter
                }
            }
        }
    }
}
