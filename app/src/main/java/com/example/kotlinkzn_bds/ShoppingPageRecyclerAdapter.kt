package com.example.kotlinkzn_bds

import androidx.annotation.NonNull
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ShoppingPageRecyclerAdapter(private val shop: List<Product>) : RecyclerView.Adapter<ShoppingPageRecyclerAdapter.ItemHolder>() {

    companion object {
         const val TAG = "shoppingRecyclerAdapter"
         const val VIEW_TYPE_NORMAL = 0
         const val VIEW_TYPE_SMALL = 1
    }

    private val largeItemHeight = 400
    private val smallItemHeight = 350

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = if (viewType == 0) {
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_page_recycler_card, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_recycling_card_small, parent, false)
        }
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val model = shop[position]

        holder.shoppingTitle.text = model.title
        Log.d(TAG, "onBindViewHolder: shopping Title" + model.title)
        val price = model.price?.toDouble()
        val priceAsString = "R$price"
        holder.shoppingPrice.text = priceAsString
        Log.d(TAG, "onBindViewHolder: " + model.price)

        holder.shoppingCard.setOnClickListener {
            val toShoppingDetails = Intent(holder.itemView.context, shopping_details::class.java)
            toShoppingDetails.putExtra("Position", position)
            holder.itemView.context.startActivity(toShoppingDetails)
        }

        Glide.with(holder.shoppingImage)
            .load(model.image)
            .centerCrop()
            .into(holder.shoppingImage)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 3 == 0) VIEW_TYPE_SMALL else VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int {
        return shop.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val shoppingCard: CardView = itemView.findViewById(R.id.CardLayoutItem)
        val shoppingImage: ImageView = itemView.findViewById(R.id.shopping_image)
        val shoppingTitle: TextView = itemView.findViewById(R.id.shopping_title)
        val shoppingPrice: TextView = itemView.findViewById(R.id.shopping_description_txt)
    }
}