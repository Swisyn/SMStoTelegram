package com.cuneytayyildiz.smstotelegram.utils.managers

import android.content.Context
import com.cuneytayyildiz.smstotelegram.utils.extensions.connectivityService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Created by Cuneyt AYYILDIZ on 4/14/2021.
 */
class ConnectionManager @Inject constructor(@ApplicationContext private val context: Context) {
    fun isConnected(): Boolean {
        val networkInfo = context.connectivityService.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected)
    }
}