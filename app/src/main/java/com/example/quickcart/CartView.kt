package com.example.quickcart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartView : AppCompatActivity() {
    private lateinit var cartView: RecyclerView
    private lateinit var total: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_view)

        cartView = findViewById(R.id.cartView)
        total = findViewById(R.id.totalPrice)

        val cart : ArrayList<BestBuyItem>? = intent.getParcelableArrayListExtra<BestBuyItem>("cart")

        var price = 0.0f
        for (item in cart!!) {
            price += item.itemPrice.toFloat()
        }
        total.text = "%.2f".format(price)


        val adapter = CartAdapter(cart!!)
        cartView.adapter = adapter
        cartView.layoutManager = LinearLayoutManager(this@CartView)
        cartView.recycledViewPool.clear()
        adapter.notifyDataSetChanged()



    }
}