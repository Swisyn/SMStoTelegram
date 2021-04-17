package com.cuneytayyildiz.smstotelegram.data.model

import android.telephony.PhoneNumberUtils


/**
 * Created by Cuneyt AYYILDIZ on 4/17/2021.
 */
data class Sms(val from: String, val messageBody: String) {
    val formattedFrom: String
        get() = PhoneNumberUtils.formatNumber(from)
}