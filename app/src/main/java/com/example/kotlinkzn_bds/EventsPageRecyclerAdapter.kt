package com.example.kotlinkzn_bds


import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventsPageRecyclerAdapter(private val events: List<Event>) :
    RecyclerView.Adapter<EventsPageRecyclerAdapter.ItemHolder>() {

    companion object {
        private const val TAG = "eventsRecyclerAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.event_page_recycler_card, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val model = events[position]

        holder.eventTitle.text = model.title
        Log.d(TAG, "onBindViewHolder: event Title" + model.title)

        val resources: Resources = holder.itemView.resources
        val isTablet = resources.getBoolean(R.bool.is_tablet)
        val margin: Int = resources.getDimensionPixelSize(if (isTablet) R.dimen.tablet_margin else R.dimen.phone_margin)
        val marginBottom: Int = resources.getDimensionPixelSize(
            if (isTablet) R.dimen.tablet_margin_bottom else R.dimen.phone_margin_bottom
        )

        Log.d(TAG, "Is Tablet: $isTablet")
        Log.d(TAG, "Selected Margin: $margin")
        val layoutParams = holder.eventCard.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(margin, 0, margin, marginBottom)
        holder.eventCard.layoutParams = layoutParams

        val originalDescription = model.description
        val words = originalDescription?.split("\\s+")?.toTypedArray()

        val truncatedDescription = StringBuilder()
        var wordCount = 0
        if (words != null) {
            for (word in words) {
                if (wordCount < 5) {
                    truncatedDescription.append(word).append(" ")
                    wordCount++
                } else {
                    break
                }
            }
        }

        var finalDescription = truncatedDescription.toString().trim()

        if (words != null) {
            if (wordCount < words.size) {
                finalDescription += "..."
            }
        }

        holder.eventDescripion.text = finalDescription
        holder.eventDate.text = getDate(model.date)

        holder.eventCard.setOnClickListener {
            val toEventDetails = Intent(it.context, EventDetails::class.java)
            toEventDetails.putExtra("eventPosition", position)
            toEventDetails.putExtra("eventTitle", model.title)
            toEventDetails.putExtra("eventImages", model.image)
            toEventDetails.putExtra("eventDescription", model.description)
            toEventDetails.putExtra("eventDate", model.date)
            it.context.startActivity(toEventDetails)
        }

        Glide.with(holder.eventImage)
            .load(model.image)
            .centerCrop()
            .into(holder.eventImage)
    }

    fun getDate(date:String?): String {
        val inputDateString = date
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val dateTime = LocalDateTime.parse(inputDateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return dateTime.format(outputFormatter)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: " + events.size)
        return events.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.findViewById(R.id.event_title)
        val eventDescripion: TextView = itemView.findViewById(R.id.event_description_txt)
        val eventImage: ImageView = itemView.findViewById(R.id.event_image)
        val eventCard: CardView = itemView.findViewById(R.id.event_cardview)
        val eventDate: TextView = itemView.findViewById(R.id.eventDate)
    }
}
