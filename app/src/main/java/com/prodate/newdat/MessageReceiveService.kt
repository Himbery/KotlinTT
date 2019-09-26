package com.prodate.newdat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageReceiveService : FirebaseMessagingService() {

    val TAG = "myLog"
    val CHANNEL_ID = "my_channel_01"


    override fun onNewToken(s: String?) {
        super.onNewToken(s)
        Log.w(TAG, "onNewToken: " + s!!)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val title = remoteMessage!!.notification!!.title
        val body = remoteMessage.notification!!.body
        val url = remoteMessage.data["url"]


        Log.w(TAG, "onMessageReceived: ")
        val intentOpen = Intent(this, StartActivity::class.java)
        intentOpen.putExtra("url", url)
        val pi = PendingIntent.getActivity(this, 0, intentOpen, PendingIntent.FLAG_ONE_SHOT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(CHANNEL_ID, title, NotificationManager.IMPORTANCE_HIGH)
            mChannel.description = "EZ"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(mChannel)
            mBuilder.setChannelId(CHANNEL_ID)
        }
        mBuilder.setContentTitle(title)
        mBuilder.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(body)
        )
        mBuilder.setContentText(body)
        mBuilder.setAutoCancel(true)
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH)
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        mBuilder.setSound(sound)
        mBuilder.setSmallIcon(R.drawable.app_tt_icon1)
        mBuilder.setContentIntent(pi)
        notificationManager.notify(java.lang.Long.toString(System.currentTimeMillis()).toString(), 1, mBuilder.build())
    }
}