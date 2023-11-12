package com.example.kotlinkzn_bds

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ShoppingRecyclerAdapter(private val shopItems: List<Product>) :
    RecyclerView.Adapter<ShoppingRecyclerAdapter.ItemHolder>() {

    companion object {
        private const val TAG = "shoppingRecyclerAdapter"
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_recycler_card, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ItemHolder, position: Int) {
        //if statement to make the last item in the list to have a margin of 15dp . change out 6 to the dataset size
        if (position == shopItems.size - 1) {
            val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            params.rightMargin =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    15f,
                    holder.itemView.resources.displayMetrics
                ).toInt()
            holder.itemView.layoutParams = params
        }

        val model = shopItems[position]

        holder.shoppingTitle.text = model.title
        Log.d(TAG, "onBindViewHolder: shopping Title" + model.title)
        val price = model.price?.toDouble()
        val priceAsString = "R$price"
        holder.shoppingPrice.text = priceAsString
        Log.d(TAG, "onBindViewHolder: " + model.price)

        holder.shoppingCard.setOnClickListener {
            val toShoppingDetails = Intent(it.context, shopping_details::class.java)
            toShoppingDetails.putExtra("Position", position)
            it.context.startActivity(toShoppingDetails)
        }

        Glide.with(holder.shoppingImage)
            .load(model.image)
            .centerCrop()
            .into(holder.shoppingImage)

//        Log.d(TAG, "onBindViewHolder: "+ imageUrl);
    }

    override fun getItemCount(): Int {
        return if (shopItems.size > 3) {
            5
        } else {
            shopItems.size
        }
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shoppingImage: ImageView = itemView.findViewById(R.id.shopping_image)
        val shoppingTitle: TextView = itemView.findViewById(R.id.shopping_title)
        val shoppingPrice: TextView = itemView.findViewById(R.id.shopping_description_txt)
        val shoppingCard: CardView = itemView.findViewById(R.id.ShoppingCard)
    }
}