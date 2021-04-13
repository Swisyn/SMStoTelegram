package com.example.smstotelegram.data

import com.example.smstotelegram.data.local.AppPreferences
import com.example.smstotelegram.data.remote.TelegramApi
import com.example.smstotelegram.data.remote.model.SendMessageRequest
import com.example.smstotelegram.data.remote.model.SendMessageResponse
import com.example.smstotelegram.data.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */


interface CallRepository {
    suspend fun sendPhoneCallLog(textMessage: String): Flow<Resource<SendMessageResponse>>
}

class CallRepositoryImpl @Inject constructor(
    private val telegramApi: TelegramApi,
    private val appPreferences: AppPreferences
) : CallRepository, RemoteDataSource() {

    override suspend fun sendPhoneCallLog(textMessage: String): Flow<Resource<SendMessageResponse>> {
        val botToken: String = appPreferences.getToken()
        val chatId: String = appPreferences.getChatId()

        return sendTelegramMessage(botToken = botToken, chatId = chatId) {
            val sendMessageRequest = SendMessageRequest(chatId = chatId, text = textMessage)

            telegramApi.sendMessage(
                token = botToken,
                sendMessageRequest = sendMessageRequest
            )
        }
    }
}