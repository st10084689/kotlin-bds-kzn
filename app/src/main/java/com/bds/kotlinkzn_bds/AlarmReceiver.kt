package com.bds.kotlinkzn_bds

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService

const val notificatioID = 1
const val channelID= "BdsKzn"
const val titleExtra= "title"
const val messageExtra= "message"


class AlarmReceiver : BroadcastReceiver() {

    companion object{
        const val TAG = "AlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {

            val i = Intent(context, MainActivity::class.java)
            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(
                context,
                123,
                i,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.mipmap.ic_notification_round)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(notificatioID, builder.build())
        }
}
