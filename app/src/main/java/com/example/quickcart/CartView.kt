package com.example.quickcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartView : AppCompatActivity() {
    private lateinit var cartView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_view)

        cartView = findViewById(R.id.cartView)

        val cart : ArrayList<BestBuyItem>? = intent.getParcelableArrayListExtra<BestBuyItem>("cart")
//        Log.d("NEW", cart!!.toString())
        val adapter = CartAdapter(cart!!)
        cartView.adapter = adapter
        cartView.layoutManager = LinearLayoutManager(this@CartView)
        cartView.recycledViewPool.clear()
        adapter.notifyDataSetChanged()



    }
}