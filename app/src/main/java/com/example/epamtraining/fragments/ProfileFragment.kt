package com.example.epamtraining.fragments


import android.annotation.SuppressLint
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
import com.example.epamtraining.models.User
import com.example.epamtraining.network.FirebaseAuth
import com.example.epamtraining.network.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlin.concurrent.thread

class ProfileFragment : Fragment() {

    private var products: List<Products> = ArrayList()
    private val url = "https://ksport-8842a.firebaseio.com/users/${FirebaseAuth.localId}/Breakfast.json"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsAdapter = BreakfastAdapter(activity)

        breakfastRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter

        }
//        showProgress()
        thread {
            products = FirebaseDatabase.getProducts(url)

            activity?.runOnUiThread {
                productsAdapter.updateItems(products)
//                hideProgress()
            }
        }
        setUserInfo()
        caloriesDiagramView.update(1500, 1400)

        breakfastTextView.setOnClickListener {
            startActivity(startProductsActivity(context))
        }
    }

    @SuppressLint("SetTextI18n")
    fun setUserInfo() {
        thread {
            val user = FirebaseDatabase.getUserInfo()
            activity?.runOnUiThread {
                usersNameTextView.text = user.name
                usersEmailTextView.text = user.email
                usersAgeTextView.text = "${user.age.toString()} years"
                usersHeights.text = "${user.height.toString()} sm"
                usersWeights.text = "${user.weight.toString()} kg"
                if (user.sex.toString().equals("Male")) {
                    usersGenderImageView.setImageResource(R.drawable.male)
                } else {
                    usersGenderImageView.setImageResource(R.drawable.female)
                }
                caloriesNeededTextView.setText(calculateCalories(user))
            }
        }
    }

    fun calculateCalories(user: User): String {
        val calories: Int
        if (user.sex.toString().equals("Male")) {
            calories = ((88.36 + (13.4 * user.weight!!) + (4.8 * user.height!!) - (5.7 * user.age!!)) * 1.5).toInt()
        } else {
            calories = ((447.6 + (9.2 * user.weight!!) + (3.1 * user.height!!) - (4.3 * user.age!!)) * 1.5).toInt()
        }

        return calories.toString()
    }
}
