package com.bds.kotlinkzn_bds

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.TimeZone

class NotificationScheduler: JobService(){
    companion object {
        const val TAG = "NotificationScheduler"

    }

    var closestEvent:Event? = null
    private var eventItems: MutableList<Event> = ArrayList()

    override fun onStartJob(p0: JobParameters?): Boolean {
        doBackGroundWork(p0)
        return true
    }

    private fun doBackGroundWork(p0: JobParameters?) {
        getEventData(p0)
    }

    override fun onStopJob(p0: JobParameters?): Boolean {

        return true
    }

//    private fun setNotification(closestEvent: Event) {
//        Log.d(TAG, "setNotification: ${closestEvent!!._id} and ${getLastNotificationId()}")
//        Log.d(TAG, "setNotification: ${closestEvent!!.date} and ${getCurrentDate()}")
//        val date = getCurrentDate()== getDate()
//        val id = closestEvent!!._id != getLastNotificationId()
//        Log.d(TAG, "setNotification: date $date")
//        Log.d(TAG, "setNotification: id $id")
//        if(closestEvent!!._id != getLastNotificationId() && getCurrentDate() == getDate()) {
//            closestEvent!!._id?.let { updateLastNotificationId(it) }
//            val alarmIntent = Intent(this, AlarmReceiver::class.java)
//            val title = closestEvent?.title
//            val message = "Event Today! Join Us"
//            alarmIntent.putExtra(titleExtra, title)
//            alarmIntent.putExtra(messageExtra, message)
//
//            val pendingIntent = PendingIntent.getBroadcast(
//                this,
//                notificationID,
//                alarmIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//            val answer = PendingIntent.getBroadcast(
//                this, notificationID, alarmIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            ) == null
//
//
//            Log.d(TAG, "Notification: $answer")
//
//            val time = getTime()
//
//                alarmManager.setExactAndAllowWhileIdle(
//                    AlarmManager.RTC_WAKEUP,
//                    time!!,
//                    pendingIntent)
//            Log.d(TAG, "Notification: notification set")
//        }
//        else {
//            Log.d(TAG, "Notification already shown today")
//        }
//    }

    private fun getTime(): Long? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("Africa/Johannesburg")

        try {

            closestEvent?.date?.let {
                val date = dateFormat.parse(it)

                date?.let {
                    Log.d(TAG, "getTime: ${date.time}")
                    return System.currentTimeMillis()
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getDate(): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")  // Adjust timezone to UTC
        val dateString = closestEvent?.date
        val date = dateString?.let { dateFormat.parse(it) }

        val dateFormatOutput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormatOutput.timeZone = TimeZone.getTimeZone("Africa/Johannesburg")

        val formattedDate = date?.let { dateFormatOutput.format(it) }

        Log.d(TAG, "getTime: $formattedDate")
        return formattedDate

    }

    private fun getLastNotificationId(): String? {
       val  preferences: SharedPreferences = getSharedPreferences("notificationSchedule" ,Context.MODE_PRIVATE)
        return preferences.getString("last_notification_id", null)
    }

//    private fun getLastNotificationDate(): String? {
//        val  preferences: SharedPreferences = getSharedPreferences("notificationSchedule" ,Context.MODE_PRIVATE)
//        return preferences.getString("last_notification_id", null)
//    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("Africa/Johannesburg")
        return dateFormat.format(Date())
    }

    private fun updateLastNotificationId(EventId: String) {
       val preferences = getSharedPreferences("notificationSchedule",Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("last_notification_id", EventId)
        editor.apply()
    }

    private fun getEventData(params: JobParameters?) {
        val apiService = ApiService()
        val call: Call<EventsResponse> = apiService.getEvents()

        call.enqueue(object : Callback<EventsResponse> {
            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.events?.let { eventItems = it as MutableList<Event> }
                    closestEvent = findClosestEvent(eventItems)
                    newNotification(closestEvent!!)
                } else {
                    jobFinished(params, false)

                    }
                    jobFinished(params, false)
            }

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure e: + failed")
                jobFinished(params, true)
            }
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

                if (closestDate == null || Math.abs(eventDate.time - presentDate.time) < Math.abs(
                        closestDate.time - presentDate.time
                    )
                ) {
                    closestDate = eventDate
                    closestEvent = event
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return closestEvent
    }

   private fun newNotification(closestEvent:Event){
       Log.d(TAG, "setNotification: ${closestEvent!!._id} and ${getLastNotificationId()}")
       Log.d(TAG, "setNotification: ${closestEvent!!.date} and ${getCurrentDate()}")
       val date = getCurrentDate()== getDate()
       val id = closestEvent!!._id != getLastNotificationId()
       Log.d(TAG, "setNotification: date $date")
       Log.d(TAG, "setNotification: id $id")

       if(closestEvent!!._id != getLastNotificationId() && getCurrentDate() == getDate()) {
           closestEvent!!._id?.let { updateLastNotificationId(it) }
           var builder = NotificationCompat.Builder(this, channelID)
               .setSmallIcon(R.mipmap.ic_notification_round)
               .setContentTitle(closestEvent.title)
               .setContentText("New Event today! Join Us")
               .setStyle(NotificationCompat.BigTextStyle()
                   .bigText(closestEvent.description))
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)

           with(NotificationManagerCompat.from(this)) {
               if (ActivityCompat.checkSelfPermission(
                       applicationContext,
                       Manifest.permission.POST_NOTIFICATIONS
                   ) != PackageManager.PERMISSION_GRANTED
               ) {
                   // TODO: Consider calling
                   //    ActivityCompat#requestPermissions
                   // here to request the missing permissions, and then overriding
                   //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                   //                                          int[] grantResults)
                   // to handle the case where the user grants the permission. See the documentation
                   // for ActivityCompat#requestPermissions for more details.
                   return
               }
               notify(123, builder.build())
           }
       }
    }
}