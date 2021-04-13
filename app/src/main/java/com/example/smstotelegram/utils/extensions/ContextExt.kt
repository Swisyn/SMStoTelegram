package com.example.smstotelegram.utils.extensions

import android.app.NotificationManager
import android.content.Context
import android.telephony.TelephonyManager


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */

inline val Context.notificationService: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

inline val Context.telephonyService: TelephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager