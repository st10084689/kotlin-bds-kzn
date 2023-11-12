package com.example.kotlinkzn_bds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsFragment : Fragment() {
    private lateinit var eventPageRecycler: RecyclerView
    private lateinit var eventsError: ImageView
    private lateinit var eventsProgressBar: ProgressBar

    private companion object {
        private const val TAG = "EventsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        init(view)
        loadEventRecycler()
        return view
    }

    private fun init(view: View) {
        eventsError = view.findViewById(R.id.eventsError)
        eventsProgressBar = view.findViewById(R.id.loading_progress_events)

        eventsError.visibility = View.GONE
        eventsProgressBar.visibility = View.VISIBLE

        // Initializing the recycler
        eventPageRecycler = view.findViewById(R.id.event_page_recycler)
        eventPageRecycler.setHasFixedSize(true)
        eventPageRecycler.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }

    private fun loadEventRecycler() {
        if (Utility.getEventItems().isEmpty()) {
            getEventData()
        } else {
            val eventAdapter = EventsPageRecyclerAdapter(Utility.getEventItems())
            eventPageRecycler.adapter = eventAdapter
            eventsProgressBar.visibility = View.GONE
        }
    }

    private fun getEventData() {
        val apiService = ApiService()
        val call: Call<EventsResponse> = apiService.getEvents()

        call.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.events?.let { Utility.setEventItems(it) }
                    val eventAdapter = EventsPageRecyclerAdapter(Utility.getEventItems())
                    eventPageRecycler.adapter = eventAdapter
                    eventsProgressBar.visibility = View.GONE
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                // Handle network failure
                Log.d(TAG, "onFailure e: + failed")
                eventsError.visibility = View.VISIBLE
                eventsProgressBar.visibility = View.GONE
            }
        })
    }
}
