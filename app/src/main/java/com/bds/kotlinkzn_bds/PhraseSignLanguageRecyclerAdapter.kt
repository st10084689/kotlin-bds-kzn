package com.bds.kotlinkzn_bds

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PhraseSignLanguageRecyclerAdapter(private val signLanguageList: List<Phrase>) :
    RecyclerView.Adapter<PhraseSignLanguageRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val signNameTextView: TextView = itemView.findViewById(R.id.signTitle)
        val signCard: CardView = itemView.findViewById(R.id.signCard)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sign_aplabet_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = signLanguageList[position]

        holder.signNameTextView.text = model.title

        holder.signCard.setOnClickListener {

            val toSignLanguageDetails = Intent(it.context, SignLanguageDetails::class.java)
            toSignLanguageDetails.putExtra("signPosition", position)
            toSignLanguageDetails.putExtra("signTitle", model.title)
            Utility.setImage(model.image)
            it.context.startActivity(toSignLanguageDetails)
        }



    }


    override fun getItemCount(): Int {
        return signLanguageList.size
    }
}