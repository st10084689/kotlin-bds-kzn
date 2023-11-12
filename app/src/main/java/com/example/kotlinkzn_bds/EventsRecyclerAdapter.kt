package com.example.kotlinkzn_bds
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventsRecyclerAdapter(private val departments: List<Int>) :
    RecyclerView.Adapter<EventsRecyclerAdapter.ItemHolder>() {

    companion object {
        private const val TAG = "eventsRecyclerAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.departments_layoutcard, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Glide.with(holder.department)
            .load(departments[position])
            .centerCrop()
            .into(holder.department)
    }

    override fun getItemCount(): Int {
        return departments.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val department: ImageView = itemView.findViewById(R.id.department_image)
    }
}
