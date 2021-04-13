package com.example.smstotelegram.utils.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.smstotelegram.utils.Constants
import com.example.smstotelegram.utils.service.ForegroundService


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
class RebootBroadcastReceiver : BroadcastReceiver() {
    private val TAG = "RebootBroadcastReceiver"
    
    override fun onReceive(context: Context?, intent: Intent?) {
        val receivedAction = intent?.action.orEmpty()
        Log.d(TAG, "onReceive intent $receivedAction")

        if (receivedAction == Constants.ACTION_BOOT_COMPLETED) {
            Log.d(TAG, "BOOT_COMPLETED")

            val frontServiceIntent = Intent(context, ForegroundService::class.java)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    context?.startForegroundService(frontServiceIntent)
                }
                else -> {
                    context?.startService(frontServiceIntent)
                }
            }
        }
    }

}