package com.cuneytayyildiz.smstotelegram.utils.extensions

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */

inline val Context.notificationService: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

inline val Context.clipboardManager: ClipboardManager
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


inline val Context.connectivityService: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.unregisterBroadcastReceiver(broadcastReceiverClass: Class<out BroadcastReceiver?>){
    val component = ComponentName(this, broadcastReceiverClass)
    val status = packageManager.getComponentEnabledSetting(component)
    if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
        packageManager
            .setComponentEnabledSetting(
                component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
    }
}
