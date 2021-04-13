package com.example.smstotelegram.utils.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.smstotelegram.data.SmsRepository
import com.example.smstotelegram.data.mapper.PduSmsMapper
import com.example.smstotelegram.data.remote.model.SendMessageResponse
import com.example.smstotelegram.data.vo.Resource
import com.example.smstotelegram.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
@AndroidEntryPoint
class SmsBroadcastReceiver :
    BroadcastReceiver() {

    @Inject
    lateinit var smsRepository: SmsRepository

    private val scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("SmsBroadcastReceiver", intent.toString())

        if ( intent.action == Constants.PROVIDER_SMS_RECEIVED) {
            PduSmsMapper(smsBundle = intent.extras).parse()?.let { smsList ->
                scope.launch {
                    smsRepository
                        .sendMessage(sms = smsList.first())
                        .collect { sendMessageResponse ->
                            handleResponse(sendMessageResponse)
                        }
                }
            }
        }
    }

    private fun handleResponse(sendMessageResponse: Resource<SendMessageResponse>) {
        Log.d(
            "SmsBroadcastReceiver",
            "sendMessageResponse = [$sendMessageResponse]"
        )

        when (sendMessageResponse) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                Log.d(
                    "SmsBroadcastReceiver",
                    sendMessageResponse.data.toString()
                )
            }
            is Resource.Failure -> {

            }
        }
    }

}