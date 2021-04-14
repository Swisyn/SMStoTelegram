package com.example.smstotelegram.utils.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import android.util.Log
import com.example.smstotelegram.data.CallRepository
import com.example.smstotelegram.data.remote.model.SendMessageResponse
import com.example.smstotelegram.data.vo.Resource
import com.example.smstotelegram.utils.ConnectionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
@AndroidEntryPoint
class CallBroadcastReceiver :
    BroadcastReceiver() {

    private val TAG = javaClass.simpleName

    @Inject
    lateinit var callRepository: CallRepository

    @Inject
    lateinit var mainScope: CoroutineScope

    @Inject
    lateinit var connectionManager: ConnectionManager

    private var state = 0

    private var number = ""

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER).orEmpty()
        } else {
            val stateStr = intent.extras?.getString(TelephonyManager.EXTRA_STATE)
            number = intent.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER).orEmpty()

            when (stateStr) {
                TelephonyManager.EXTRA_STATE_IDLE -> state = TelephonyManager.CALL_STATE_IDLE
                TelephonyManager.EXTRA_STATE_RINGING -> state = TelephonyManager.CALL_STATE_RINGING
            }
        }

        onCallStateChanged(state, number)
    }

    private fun onCallStateChanged(state: Int, number: String) {
        if (number.isNotEmpty()) {
            val formattedPhoneNumber = PhoneNumberUtils.formatNumber(number)
            val callState = when (state) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    "Incoming Call from $formattedPhoneNumber"
                }
                TelephonyManager.CALL_STATE_IDLE -> {
                    "Missed Call from $formattedPhoneNumber"
                }
                else -> "unknown"
            }

            sendCallState(callState)
        }
    }

    private fun sendCallState(callState: String) {
        if (connectionManager.isConnected()) {
            mainScope.launch {
                callRepository
                    .sendPhoneCallLog(textMessage = callState)
                    .collect {
                        handleResponse(it)
                    }
            }
        } else {
            Log.w(TAG, "There is no active internet connection")
        }
    }

    private fun handleResponse(sendMessageResponse: Resource<SendMessageResponse>) {
        Log.d(
            TAG,
            "sendMessageResponse = [$sendMessageResponse]"
        )

        when (sendMessageResponse) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                Log.d(
                    TAG,
                    sendMessageResponse.data.toString()
                )
            }
            is Resource.Failure -> {

            }
        }
    }

}