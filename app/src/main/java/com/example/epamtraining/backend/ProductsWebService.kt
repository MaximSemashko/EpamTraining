package com.example.epamtraining.backend

import com.example.epamtraining.models.Products
import com.squareup.okhttp.OkHttpClient
import java.util.concurrent.Executors

class ProductsWebService : IProductsWebService {

    private val client = OkHttpClient()
    private val executor = Executors.newSingleThreadExecutor()

    override fun loadProducts(result: ICallback<List<Products>>) {
        executor.execute {
//            try {
//                val request = Request.Builder().url("https://api.thecatapi.com/v1/images/search?limit=$pQuantity").build()
//
//                pResult.onResult(parseResponse(client.newCall(request).execute()))
//            } catch (pE: IOException) {
//                pResult.onResult(emptyList<String>())
//            } catch (pE: JSONException) {
//                pResult.onResult(emptyList<String>())
//            }
        }
    }
}