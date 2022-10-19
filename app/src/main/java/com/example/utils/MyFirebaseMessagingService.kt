package com.example.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.datingapp.MainActivity
import com.example.datingapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val channelId = "datingApps"

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val myIntent = Intent(this,MainActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
        createNotificationChannel(manager as NotificationManager)


        val myPendingIntent = PendingIntent.getActivities(this,0, arrayOf(myIntent),PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this,channelId)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setContentIntent(myPendingIntent)
            .build()

        manager.notify(Random.nextInt(),notification)

    }

    private fun createNotificationChannel(manager: NotificationManager){
        val channel = NotificationChannel(channelId,"datingAppsChat",NotificationManager.IMPORTANCE_HIGH)

        channel.description = "New Chat"
        channel.enableLights(true)

        manager.createNotificationChannel(channel)
    }
}

