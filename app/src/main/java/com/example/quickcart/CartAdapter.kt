package com.example.quickcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private val cart: ArrayList<BestBuyItem>
): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val productName: TextView
        val productPrice: TextView
        val productImage: ImageView
        val productDesc: TextView

        init {
            productName = view.findViewById(R.id.productName)
            productPrice = view.findViewById(R.id.productPrice)
            productImage = view.findViewById(R.id.productImage)
            productDesc = view.findViewById(R.id.desc)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cart.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr = cart[position]

        holder.productName.text = curr.itemName
        holder.productPrice.text = "\$${curr.itemPrice}"
        holder.productDesc.text = curr.description.substring(0, 200 )

        Glide.with(holder.itemView).load(curr.itemImageUrl).fitCenter().into(holder.productImage)


    }
}