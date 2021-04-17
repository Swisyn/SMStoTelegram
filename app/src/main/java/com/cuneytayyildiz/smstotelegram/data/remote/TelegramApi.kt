package com.cuneytayyildiz.smstotelegram.data.remote

import com.cuneytayyildiz.smstotelegram.data.remote.model.SendMessageRequest
import com.cuneytayyildiz.smstotelegram.data.remote.model.SendMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
interface TelegramApi {

    @POST("/bot{token}/sendMessage")
    suspend fun sendMessage(
        @Path("token") token: String,
        @Body sendMessageRequest: SendMessageRequest
    ): Response<SendMessageResponse>

}