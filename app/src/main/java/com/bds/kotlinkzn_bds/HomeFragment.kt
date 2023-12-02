package com.bds.kotlinkzn_bds

import android.content.Context
import android.util.Log

import android.view.LayoutInflater

import android.view.MotionEvent

import android.view.View

import android.view.ViewGroup
import android.content.Intent

import android.os.Bundle
import android.os.Handler


import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView


import android.view.animation.Animation

import android.view.animation.AnimationUtils

import android.widget.Button

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout

import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.abs
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


class HomeFragment : Fragment() {

    private lateinit var eventsRecycler: RecyclerView
    private lateinit var shoppingRecycler: RecyclerView
    private lateinit var eventsError: ImageView
    private lateinit var shoppingError: ImageView
    private lateinit var donationButton: Button
    private lateinit var eventButton: TextView
    private lateinit var shoppingButton: TextView
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var eventsProg: ProgressBar
    private lateinit var shoppingProg: ProgressBar
    private lateinit var signLanguageProg: ProgressBar
    private var listener: FragmentInteractionListener? = null
    private lateinit var signLanguageContent: RelativeLayout



    private var sortedEventList: MutableList<Event> = mutableListOf()

    private lateinit var eventIcon: ImageView
    private lateinit var eventText: TextView
    private lateinit var eventUnderline: ImageView

    private lateinit var shoppingIcon: ImageView
    private lateinit var shoppingText: TextView
    private lateinit var shoppingUnderline: ImageView
    private lateinit var chosenUnderline: ImageView


    companion object {
        private const val TAG = "HomeFragment"
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        getEventData()
        getShoppingData()
        init(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionListener) {
            listener = context

            // Initialize the views from MainActivity
            eventIcon = listener?.eventIcon ?: ImageView(context)
            eventText = listener?.eventText ?: TextView(context)
            eventUnderline = listener?.eventUnderline ?: ImageView(context)

            shoppingIcon = listener?.shoppingIcon ?: ImageView(context)
            shoppingText = listener?.shoppingText ?: TextView(context)
            shoppingUnderline = listener?.shoppingUnderline ?: ImageView(context)

            chosenUnderline = listener?.chosenUnderline ?: ImageView(context)
        } else {
            throw RuntimeException("$context must implement FragmentInteractionListener")
        }
    }

    private fun init(view: View) {
        // Initializing the util class
        val util = Utility
        // Initializing the event recycler
        eventsRecycler = view.findViewById(R.id.events_recycler)
        eventsRecycler.setHasFixedSize(true)
        eventsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initializing the event recycler
        shoppingRecycler = view.findViewById(R.id.shopping_recycler)
        shoppingRecycler.setHasFixedSize(true)
        shoppingRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        shoppingProg = view.findViewById(R.id.loading_progress_shopping)
        eventsProg = view.findViewById(R.id.loading_progress_events)
        eventsProg.visibility = View.VISIBLE
        shoppingProg.visibility = View.VISIBLE



        eventsError = view.findViewById(R.id.eventsError)
        shoppingError = view.findViewById(R.id.shoppingError)

        donationButton = view.findViewById(R.id.donation_button_background)
        eventButton = view.findViewById(R.id.event_see_all_txt)
        shoppingButton = view.findViewById(R.id.see_all_txt)

        eventButton.setOnClickListener {
            val eventsFragment = EventsFragment()
            listener?.loadFragment(eventsFragment)
            Handler().postDelayed({
                listener?.changeNavBarColors(
                    R.drawable.events_icon_selected,
                    eventIcon ?: ImageView(context),
                    eventText ?: TextView(context),
                    eventUnderline ?: ImageView(context)
                )
                listener?.animateUnderline(listener?.chosenUnderline ?: ImageView(context), listener?.eventUnderline ?: ImageView(context))
                listener?.chosenUnderline = listener?.eventUnderline ?: ImageView(context)
            }, 250)
        }

        shoppingButton.setOnClickListener {
            val shoppingFragment = ShoppingFragment()
            listener?.loadFragment(shoppingFragment)
            Handler().postDelayed({
                listener?.changeNavBarColors(
                    R.drawable.shopping_icon_selected,
                    shoppingIcon ?: ImageView(context),
                    shoppingText ?: TextView(context),
                    shoppingUnderline ?: ImageView(context)
                )
                listener?.animateUnderline(listener?.chosenUnderline ?: ImageView(context), listener?.shoppingUnderline ?: ImageView(context))
                listener?.chosenUnderline = listener?.shoppingUnderline ?: ImageView(context)
            }, 250)
        }
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)

        donationButton.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> donationButton.startAnimation(scaleUp)
                MotionEvent.ACTION_UP -> donationButton.startAnimation(scaleDown)
            }
            false
        }

        scaleDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val toClass = Intent(context, DonationActivity::class.java)
                startActivity(toClass)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    fun findClosestEvent(events: List<Event>): Event? {
        var closestDate: Date? = null
        var closestEvent: Event? = null

        val dateFormat= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val presentDate = Date()

        for (event in events) {
            try {
                val eventDate: Date = dateFormat.parse(event.date)

                if (closestDate == null || abs(eventDate.time - presentDate.time) < abs(closestDate.time - presentDate.time)) {
                    closestDate = eventDate
                    closestEvent = event
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        return closestEvent
    }


    private fun getShoppingData() {
        val apiService = ApiService()

        val call = apiService.getShopping()

        call.enqueue(object : Callback<ShoppingResponse> {
            override fun onResponse(
                call: Call<ShoppingResponse>,
                response: Response<ShoppingResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.products?.let { Utility.setShoppingItems(it) }
                    val shoppingAdapter = ShoppingRecyclerAdapter(Utility.getShoppingItems())
                    shoppingRecycler.adapter = shoppingAdapter
                    shoppingProg.visibility = View.GONE

                } else {

                }
            }

            override fun onFailure(call: Call<ShoppingResponse>, t: Throwable) {
                shoppingError.visibility = View.VISIBLE
                shoppingProg.visibility = View.GONE
            }
        })
    }

    private fun getEventData() {
        val apiService = ApiService()

        val call = apiService.getEvents()

        call.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.events?.let { Utility.setEventItems(it) }
                    val closestEvent = findClosestEvent(Utility.getEventItems())
                    closestEvent?.let {
                        sortedEventList.add(it)
                        listener?.onEventLoaded(sortedEventList)
                        val eventAdapter = EventsPageRecyclerAdapter(sortedEventList)
                        eventsRecycler.adapter = eventAdapter
                        eventsProg.visibility = View.GONE
                    }
                } else {
                    Log.d(TAG, "onResponse: failed: error code " )
                }

            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {

                Log.d(TAG, "onFailure: + failed$t call: $call")
                eventsError.visibility = View.VISIBLE
                eventsProg.visibility = View.GONE
            }
        })
    }


    interface FragmentInteractionListener {
        fun onEventLoaded(events: List<Event>)
        fun loadFragment(fragment: Fragment)
        fun changeNavBarColors(drawableValue: Int, image: ImageView, text: TextView, underLine: ImageView)
        fun animateUnderline(chosenUnderline: ImageView, targetUnderline: ImageView)

        var eventIcon: ImageView?
        var eventText: TextView?
        var eventUnderline: ImageView?
        var shoppingIcon: ImageView?
        var shoppingText: TextView?
        var shoppingUnderline: ImageView?
        var chosenUnderline: ImageView?
    }


}
