package com.cuneytayyildiz.smstotelegram.utils.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.cuneytayyildiz.smstotelegram.utils.managers.ServiceNotificationManager


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */

private const val ONGOING_NOTIFICATION_ID = 123123

class ForegroundService : Service() {

    private var notificationManager: ServiceNotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        notificationManager = ServiceNotificationManager(context = this)

        notificationManager?.buildNotification()?.let { notification ->
            startForeground(ONGOING_NOTIFICATION_ID, notification)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onDestroy() {
        super.onDestroy()
        notificationManager = null
        startService(Intent(this, ForegroundService::class.java))
    }
}