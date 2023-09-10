package com.waterreminder.FCM

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.waterreminder.utils.NotificationUtils
import org.koin.android.ext.android.inject


class FCMReceiver: FirebaseMessagingService() {

    private val notificationUtils: NotificationUtils by inject()

    override fun onNewToken(token: String) {
        Log.d("TESTE_TOKEN",token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        notificationUtils.showNotification(message.notification?.title, message.notification?.body)
    }

}