package com.example.smstotelegram.utils

import android.content.Context
import com.example.smstotelegram.utils.extensions.connectivityService


/**
 * Created by Cuneyt AYYILDIZ on 4/14/2021.
 */
class ConnectionManager(private val context: Context) {
    fun isConnected(): Boolean {
        val networkInfo = context.connectivityService.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected)
    }
}