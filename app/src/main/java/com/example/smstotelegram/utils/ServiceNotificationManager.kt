package com.example.smstotelegram.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.smstotelegram.R
import com.example.smstotelegram.ui.MainActivity
import com.example.smstotelegram.utils.extensions.notificationService


/**
 * Created by Cuneyt AYYILDIZ on 4/13/2021.
 */
class ServiceNotificationManager(private val context: Context) {

    private val notificationChannelId = context.getString(R.string.notification_channel_id)
    private val notificationChannelName = context.getString(R.string.notification_channel_name)

    fun buildNotification(): Notification {
        return NotificationCompat.Builder(
            context,
            notificationChannelId
        ).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentText(context.getString(R.string.notification_content_text))
            setContentIntent(createPendingIntent())

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    val notificationChannel = createNotificationChannel()
                    context.notificationService.createNotificationChannel(notificationChannel)
                }
            }
        }.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            NotificationManager.IMPORTANCE_MIN
        ).apply {
            enableLights(false)
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_SECRET
        }
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}