package com.example.smstotelegram.utils.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.example.smstotelegram.data.SmsRepository
import com.example.smstotelegram.data.mapper.PduSmsMapper
import com.example.smstotelegram.data.remote.model.SendMessageResponse
import com.example.smstotelegram.data.vo.Resource
import com.example.smstotelegram.di.annotations.MainDispatcher
import com.example.smstotelegram.utils.ConnectionManager
import com.example.smstotelegram.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
@AndroidEntryPoint
class SmsBroadcastReceiver :
    BroadcastReceiver() {

    private val TAG = javaClass.simpleName

    @Inject
    lateinit var smsRepository: SmsRepository

    @Inject
    @MainDispatcher
    lateinit var mainScope: CoroutineScope

    @Inject
    lateinit var connectionManager: ConnectionManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Constants.PROVIDER_SMS_RECEIVED) {
            PduSmsMapper(smsBundle = intent.extras).parse()?.let { smsList ->
                sendSmsData(smsList)
            }
        }
    }

    private fun sendSmsData(smsList: MutableList<SmsMessage>) {
        if (connectionManager.isConnected()) {
            mainScope.launch {
                smsRepository
                    .sendMessage(sms = smsList.first())
                    .collect { sendMessageResponse ->
                        handleResponse(sendMessageResponse)
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