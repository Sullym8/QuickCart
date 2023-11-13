package com.example.quickcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.math.round

class BestBuyItemAdapter(private val itemObjectList: List<BestBuyItem>,
    private val shoppingCart: MutableList<BestBuyItem>,
    private val totalCount: TextView,
    private val totalPrice: TextView,
    ) : RecyclerView.Adapter<BestBuyItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView
        val productPrice: TextView
        val productAdd: Button
        val productRemove: Button
        val productImage: ImageView

        init {
            productName = view.findViewById(R.id.productName)
            productPrice = view.findViewById(R.id.productPrice)
            productAdd = view.findViewById(R.id.buttonAdd)
            productRemove = view.findViewById(R.id.buttonDetails)
            productImage = view.findViewById(R.id.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemObjectList.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curr = itemObjectList[position]

        holder.productName.text = curr.itemName
        holder.productPrice.text = "\$${curr.itemPrice}"

        Glide.with(holder.itemView).load(curr.itemImageUrl).fitCenter().into(holder.productImage)

        holder.productAdd.setOnClickListener {
            shoppingCart.add(curr)
            var count = 0
            var price = 0.0f
            for (item in shoppingCart) {
                count += 1
                price += item.itemPrice.toFloat()
            }
            totalCount.text = count.toString()
            totalPrice.text = "%.2f".format(price)
        }

    }
}