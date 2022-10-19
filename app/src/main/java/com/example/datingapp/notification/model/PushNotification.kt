package com.example.datingapp.notification.model

data class PushNotification(
    val data: NotificationData,
    val to: String? = ""
)
