package com.bds.kotlinkzn_bds

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteAction
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

const val channelId = "notification_Channel"
const val channelName = "com.bds.kotlinkzn_bds"

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if(message.notification !=null){
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    fun getRemoteView(title: String, message: String): RemoteViews{
        val remoteView = RemoteViews("com.bds.kotlinkzn_bds",R.layout.notification_card)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.desc,message)
        remoteView.setImageViewResource(R.id.notification_logo,R.mipmap.ic_notification_round)

        return remoteView
    }

            fun generateNotification(title:String,message: String ){

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                val pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )


                var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
                    .setSmallIcon(R.mipmap.ic_notification_round)
                    .setAutoCancel(true)
                    .setVibrate(longArrayOf(1000,1000,1000,1000))
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)

                builder = builder.setContent(getRemoteView(title,message))

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val notificationChannel = NotificationChannel(channelID, channelName,NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)

                notificationManager.notify(0,builder.build())
            }


}