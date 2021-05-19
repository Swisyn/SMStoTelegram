package com.cuneytayyildiz.smstotelegram.utils.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.cuneytayyildiz.smstotelegram.data.SmsRepository
import com.cuneytayyildiz.smstotelegram.data.mapper.PduSmsMapper
import com.cuneytayyildiz.smstotelegram.data.model.Sms
import com.cuneytayyildiz.smstotelegram.data.remote.model.SendMessageResponse
import com.cuneytayyildiz.smstotelegram.data.vo.Resource
import com.cuneytayyildiz.smstotelegram.utils.Constants
import com.cuneytayyildiz.smstotelegram.utils.managers.ConnectionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            CoroutineScope(Dispatchers.Default).launch {
                val sms = if (smsList.size == 1) {
                    val receivedSms = smsList.first()
                    Sms(
                        from = receivedSms.originatingAddress.orEmpty(),
                        messageBody = receivedSms.displayMessageBody
                    )
                } else {
                    smsList.groupBy { it.originatingAddress }.map { entry ->
                        Sms(
                            from = entry.key.orEmpty(),
                            messageBody = entry.value.joinToString(separator = " ") { sms ->
                                sms.displayMessageBody
                            })
                    }.first()
                }

                smsRepository
                    .sendMessage(
                        sms = sms
                    )
                    .collect { sendMessageResponse ->
                        handleResponse(sendMessageResponse)
                    }
            }
        } else {
            Log.w(TAG, "There is no active internet connection!")
        }
    }

    private fun handleResponse(sendMessageResponse: Resource<SendMessageResponse>) {
        when (sendMessageResponse) {
            is Resource.Success -> {
                Log.d(
                    TAG,
                    sendMessageResponse.data.toString()
                )
            }
            is Resource.Failure -> {
                sendMessageResponse.exception.printStackTrace()
            }
        }
    }

}