package com.example.kotlinkzn_bds

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecoration(private val smallItemHeight: Int, private val largeItemHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        if (position % 4 == 0) {
            setItemViewHeight(view, largeItemHeight)
        } else {
            setItemViewHeight(view, smallItemHeight)
        }
    }

    private fun setItemViewHeight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.layoutParams = params
    }
}
