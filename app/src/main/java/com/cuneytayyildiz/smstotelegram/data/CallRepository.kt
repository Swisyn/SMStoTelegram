package com.cuneytayyildiz.smstotelegram.data

import com.cuneytayyildiz.smstotelegram.data.local.AppPreferences
import com.cuneytayyildiz.smstotelegram.data.remote.TelegramApi
import com.cuneytayyildiz.smstotelegram.data.remote.base.createApiCall
import com.cuneytayyildiz.smstotelegram.data.remote.model.SendMessageRequest
import com.cuneytayyildiz.smstotelegram.data.remote.model.SendMessageResponse
import com.cuneytayyildiz.smstotelegram.data.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
) : CallRepository {
    override suspend fun sendPhoneCallLog(textMessage: String): Flow<Resource<SendMessageResponse>> {
        return flow {
            val tokenId: String = appPreferences.getTokenId()
            val chatId: String = appPreferences.getChatId()

            if (tokenId.isNotEmpty() && chatId.isNotEmpty()) {
                val apiResponse = createApiCall {
                    val sendMessageRequest = SendMessageRequest(chatId = chatId, text = textMessage)

                    telegramApi.sendMessage(
                        token = tokenId,
                        sendMessageRequest = sendMessageRequest
                    )
                }
                emitAll(apiResponse)
            } else {
                emit(
                    Resource.Failure(
                        exception = Exception("botToken or chatId was empty!")
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}