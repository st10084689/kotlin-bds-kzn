package com.bds.kotlinkzn_bds

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AlphabetSignLanguageRecyclerAdapter(private val signLanguageList: List<Sign>) :
    RecyclerView.Adapter<AlphabetSignLanguageRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val signNameTextView: TextView = itemView.findViewById(R.id.signTitle)
        val colorBar: View = itemView.findViewById(R.id.colorbar)
        val signCard: CardView = itemView.findViewById(R.id.signCard)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sign_aplabet_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = signLanguageList[position]

        holder.signNameTextView.text = model.title//sets text to the last
        holder.colorBar.setBackgroundColor(Color.parseColor(setColorBar(position)))//set the background to a color base on item postition

        holder.signCard.setOnClickListener {

            val toSignLanguageDetails = Intent(it.context, SignLanguageDetails::class.java)
            toSignLanguageDetails.putExtra("signPosition", position)
            toSignLanguageDetails.putExtra("signTitle", model.title)
            Utility.setImage(model.image)
            it.context.startActivity(toSignLanguageDetails)
        }

    }


    //method that returns a hex value based on the position of the item
    private fun setColorBar(position: Int): String{

        when(position) {

            1 -> {
                return "#F92020"
            }

            2 -> {
                return "#F95420"
            }

            3 -> {
                return "#F97B20"
            }

            4 -> {
                return "#F9A220"
            }

            5 -> {
                return "#F9C920"
            }

            6 -> {
                return "#F9E320"
            }

            7 -> {
                return "#DAF920"
            }

            8 -> {
                return "#A6F920"
            }

            9 -> {
                return "#59F920"
            }

            10 -> {
                return "#20F929"
            }

            11 -> {
                return "#20F95D"
            }
            12-> {
                return "#20F984"
            }
            13 -> {
                return "#20F9B8"
            }
            14 -> {
                return "#20F9F9"
            }
            15 -> {
                return "#20C5F9"
            }
            16 -> {
                return "#2091F9"
            }
            17 -> {
                return "#205DF9"
            }
            18 -> {
                return "#3F20F9"
            }
            19-> {
                return "#9920F9"
            }
            20 -> {
                return "#CD20F9"
            }
            21 -> {
                return "#F420F9"
            }
            22 -> {
                return "#F920E3"
            }
            23 -> {
                return "#F920BC"
            }
            24 -> {
                return "#F92088"
            }
            25 -> {
                return "#F92054"
            }
            26 -> {
                return "#F92054"
            }
            else ->{
                return "#F92054"
            }
        }

    }


    override fun getItemCount(): Int {
        return signLanguageList.size
    }
}