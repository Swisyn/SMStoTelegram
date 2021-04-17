package com.cuneytayyildiz.smstotelegram.data.remote.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
data class SendMessageRequest(
    @SerializedName("chat_id") val chatId: String,
    @SerializedName("text") val text: String
)