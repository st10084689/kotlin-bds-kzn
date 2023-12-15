package com.bds.kotlinkzn_bds

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

const val notificationID = 1
const val channelID= "BdsKzn"
const val titleExtra= "title"
const val messageExtra= "message"


class AlarmReceiver : BroadcastReceiver() {

    companion object{
        const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            context,
            123,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

// Building the notification using NotificationCompat.Builder
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.mipmap.ic_notification_round)
            .setContentTitle(intent.getStringExtra(titleExtra)) // Setting notification title
            .setContentText(intent.getStringExtra(messageExtra)) // Setting notification content text
            .setAutoCancel(true)// Automatically removes the notification when clicked
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Using default notification settings
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Setting notification priority
            .setContentIntent(pendingIntent) // Setting the PendingIntent for when the notification is clicked

        Log.d(TAG, "onReceive:${intent.getStringExtra(titleExtra)}")
        Log.d(TAG, "onReceive:${intent.getStringExtra(messageExtra)}")

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager// Getting the NotificationManager service

        manager.notify(notificationID, builder.build())

        }
}
