package com.bds.kotlinkzn_bds

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShoppingFragment : Fragment() {

    private lateinit var shoppingViewLeft: RecyclerView
    private lateinit var shoppingError: ImageView
    private lateinit var shoppingProg: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)
        init(view)
        loadShoppingRecycler()
        return view
    }

    private fun init(view: View) {
        shoppingProg = view.findViewById(R.id.loading_progress_shopping)
        shoppingError = view.findViewById(R.id.shoppingError)
        shoppingProg.visibility = View.VISIBLE
        shoppingError.visibility = View.GONE

        shoppingViewLeft = view.findViewById(R.id.shopping_page_recycler)
        shoppingViewLeft.setHasFixedSize(true)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        shoppingViewLeft.layoutManager = layoutManager
    }

    private fun loadShoppingRecycler() {
        if (Utility.getShoppingItems().isEmpty()) {
            getData()
        } else {
            val shoppingAdapter = ShoppingPageRecyclerAdapter(Utility.getShoppingItems())
            shoppingViewLeft.adapter = shoppingAdapter
            shoppingProg.visibility = View.GONE
        }
    }

    private fun getData() {
        val apiService = ApiService()

        val call = apiService.getShopping()

        call.enqueue(object : Callback<ShoppingResponse> {
            override fun onResponse(call: Call<ShoppingResponse>, response: Response<ShoppingResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    Utility.setShoppingItems(response.body()?.products ?: emptyList())
                    val shoppingAdapter = ShoppingPageRecyclerAdapter(Utility.getShoppingItems())
                    shoppingViewLeft.adapter = shoppingAdapter
                    shoppingProg.visibility = View.GONE
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<ShoppingResponse>, t: Throwable) {
                shoppingProg.visibility = View.GONE
                shoppingError.visibility = View.VISIBLE
            }
        })
    }
}

