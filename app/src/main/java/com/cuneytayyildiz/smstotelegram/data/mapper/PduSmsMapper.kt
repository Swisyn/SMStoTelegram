package com.cuneytayyildiz.smstotelegram.data.mapper

import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
class PduSmsMapper(private val smsBundle: Bundle?) {
    private val KEY_SMS_CONTAINER_ID = "pdus"

    fun parse(): MutableList<SmsMessage>? {
        return if (smsBundle != null && smsBundle.containsKey(KEY_SMS_CONTAINER_ID)) {
            val smsFormat = smsBundle.getString("format")

            val smsArray = smsBundle.get(KEY_SMS_CONTAINER_ID) as Array<*>

            if (smsArray.isNotEmpty()) {
                return smsArray.map { pdu ->
                    val pduArray = pdu as ByteArray?
                    getSmsMessage(pdu = pduArray, format = smsFormat)
                }.toMutableList()
            } else null
        } else null
    }

    private fun getSmsMessage(pdu: ByteArray?, format: String?): SmsMessage {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> SmsMessage.createFromPdu(
                pdu,
                format
            )
            else -> SmsMessage.createFromPdu(pdu)
        }
    }
}