package com.example.quickcart

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var itemObjectList: MutableList<BestBuyItem>
    private lateinit var shoppingCart: MutableList<BestBuyItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: EditText
    private lateinit var totalPrice: TextView
    private lateinit var totalCount : TextView
    private lateinit var clear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemObjectList = mutableListOf()
        shoppingCart = mutableListOf()
        recyclerView = findViewById(R.id.products)
        searchView = findViewById(R.id.search)
        totalPrice = findViewById(R.id.totalPrice)
        totalCount = findViewById(R.id.totalCount)
        clear = findViewById(R.id.clearButton)

        searchView.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                itemObjectList.clear()
                getListings(p0.toString())
            }

        })

        clear.setOnClickListener {
            this.totalPrice.text = "0.00"
            this.totalCount.text = "0"
            this.shoppingCart.clear()
        }


        getListings("")
    }

    private fun getListings(searchTerm: String) {
        val client = AsyncHttpClient()
        var searchURL = ""
        var param = ""
        if (searchTerm.isEmpty()) {
            searchURL = "https://api.bestbuy.com/v1/products((categoryPath.id=pcmcat209400050001))?apiKey=qhqws47nyvgze2mq3qx4jadt&sort=name.asc&show=name,regularPrice,image&pageSize=100&format=json"
        } else {
            for (term in searchTerm.split(" ")) {
                param += if(param.isEmpty()){
                    "search=${term}"
                } else {
                    "&search=${term}"
                }
            }
            searchURL = "https://api.bestbuy.com/v1/products((${param}))?apiKey=qhqws47nyvgze2mq3qx4jadt&sort=name.asc&show=name,regularPrice,image&pageSize=100&format=json"
        }

        client[searchURL, object :
            JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("FAILURE", searchURL)
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d("SUCESS", "${json}")
                val resp = json!!.jsonObject
                val items = resp.getJSONArray("products")
                for (i in 0 until items.length()) {
                    val curr = items.getJSONObject(i)
                    itemObjectList.add(
                        BestBuyItem(
                            curr.getString("name"),
                            curr.getString("regularPrice"),
                            curr.getString("image")
                        )
                    )
                }

                Log.d("DONE", "$itemObjectList")

                val adapter = BestBuyItemAdapter(itemObjectList,shoppingCart, totalCount, totalPrice)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.recycledViewPool.clear()
                adapter.notifyDataSetChanged()
            }

        }]

    }
}

data class BestBuyItem(
    val itemName: String,
    val itemPrice: String,
    val itemImageUrl: String
)