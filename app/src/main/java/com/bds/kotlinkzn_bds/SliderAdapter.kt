package com.bds.kotlinkzn_bds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter


class SliderAdapter(private val context: Context) : PagerAdapter() {

    private val images = arrayOf(R.drawable.event_details_place_holder, R.drawable.event_details_place_holder, R.drawable.event_details_place_holder)

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.event_details_slider_item, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.EventDetailsImage)
        imageView.setImageResource(images[position])

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}