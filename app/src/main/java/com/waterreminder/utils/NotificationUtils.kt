package com.waterreminder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.waterreminder.R
import com.waterreminder.ui.MainActivity

class NotificationUtils(val context: Context){

    val CHANNEL_ID = "DrinkNow"

    init {
    createNotificationChannel()
    }

    fun showNotification(){

        //val notificationLayout = RemoteViews("com.isaaclabs.bebaja",R.layout.notification)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val largeIcon = BitmapFactory.decodeResource(context.resources,R.drawable.ic_notification_icon)
        val builder = NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setLargeIcon(largeIcon)
            .setColor(context.getColor(R.color.background))
            .setContentTitle("DrinkNow")
            .setContentText("Hora de tomar um copo de água")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if(this.areNotificationsEnabled()){
                notify(5, builder.build())
            }
        }


    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, descriptionText, importance)
            // Register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
    }