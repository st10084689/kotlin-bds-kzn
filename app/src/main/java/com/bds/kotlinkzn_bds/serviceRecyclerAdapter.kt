package com.bds.kotlinkzn_bds

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ServiceRecyclerAdapter(private val serviceList: List<Service>) :
    RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceTitle)
        val serviceNameImageView: ImageView = itemView.findViewById(R.id.serviceImage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_item_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = serviceList[position]

        holder.serviceNameTextView.text = model.name

        holder.serviceNameImageView.setBackgroundResource(model.image)

        Glide.with(holder.serviceNameImageView)
            .load(model.image)
            .centerCrop()
            .into(holder.serviceNameImageView)


    }


    override fun getItemCount(): Int {
        return serviceList.size
    }
}