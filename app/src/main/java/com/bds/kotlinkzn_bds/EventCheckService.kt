package com.bds.kotlinkzn_bds

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val TAG = "EventCheckService"
//TODO:properly implement notification service
//class EventCheckService: IntentService("EventCheckService") {
//
//
//    var closestEvent:Event? = null
//
//    override fun onHandleIntent(intent: Intent?) {
//
//       getEventData()
//    }
//
//    private fun setNotification(){
//        val intent = Intent(applicationContext, AlarmReceiver::class.java)
//        val title = closestEvent?.title
//        val message = closestEvent?.description
//        intent.putExtra(titleExtra, title)
//        intent.putExtra(messageExtra, message)
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            applicationContext,
//            notificatioID,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or  PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        val  alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val time = getTime()
//
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            time,
//            pendingIntent)
//
//        Log.d(TAG, "setNotification: time scheduled for "+ time)
//    }
//
//
//
//    private fun getTime(): Long {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
//        dateFormat.timeZone = TimeZone.getTimeZone("Africa/Johannesburg")
//
//        try {
//            Log.d(TAG, "c: "+ closestEvent!!.date)
//            closestEvent?.date?.let {
//                val date = dateFormat.parse(it)
//
//                date?.let {
//                    return System.currentTimeMillis()
//                }
//            }
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//
//        return System.currentTimeMillis()
//    }
//
//    private fun getEventData() {
//        val apiService = ApiService()
//
//        val call = apiService.getEvents()
//
//        call.enqueue(object : Callback<EventsResponse> {
//            override fun onResponse(
//                call: Call<EventsResponse>,
//                response: Response<EventsResponse>
//            ) {
//                if (response.isSuccessful && response.body() != null) {
//                     closestEvent = findClosestEvent(response.body()!!.events!!)
//                    setNotification()
//                } else {
//
//                }
//            }
//
//            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
//
//
//            }
//        })
//    }
//
//    fun findClosestEvent(events: List<Event>): Event? {
//        var closestDate: Date? = null
//        var closestEvent: Event? = null
//
//        val dateFormat= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
//        val presentDate = Date()
//
//        for (event in events) {
//            try {
//                val eventDate: Date = dateFormat.parse(event.date)
//
//                if (closestDate == null || Math.abs(eventDate.time - presentDate.time) < Math.abs(
//                        closestDate.time - presentDate.time
//                    )
//                ) {
//                    closestDate = eventDate
//                    closestEvent = event
//                }
//            } catch (e: ParseException) {
//                e.printStackTrace()
//            }
//        }
//
//        return closestEvent
//    }
//}