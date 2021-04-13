package com.example.smstotelegram.utils.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.smstotelegram.R
import com.example.smstotelegram.ui.MainActivity
import com.example.smstotelegram.utils.extensions.notificationService


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
class ForegroundService : Service() {

    private val ONGOING_NOTIFICATION_ID = 123123

    override fun onCreate() {
        super.onCreate()
        val notificationChannelId = getString(R.string.notification_channel_id)
        val notificationChannelName = getString(R.string.notification_channel_name)

        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            notificationChannelId
        ).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentText(getString(R.string.notification_content_text))
            setContentIntent(createPendingIntent())

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    val notificationChannel =
                        createNotificationChannel(notificationChannelId, notificationChannelName)
                    notificationService.createNotificationChannel(notificationChannel)
                }
            }
        }
        val notification = notificationBuilder.build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationChannelId: String,
        notificationChannelName: String
    ): NotificationChannel {
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
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        super.onDestroy()

        startService(Intent(this, ForegroundService::class.java))
    }
}