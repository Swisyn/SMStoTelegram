package com.example.smstotelegram.data

import android.telephony.PhoneNumberUtils
import android.telephony.SmsMessage
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

interface SmsRepository {
    suspend fun sendMessage(sms: SmsMessage): Flow<Resource<SendMessageResponse>>
}

class SmsRepositoryImpl @Inject constructor(
    private val telegramApi: TelegramApi,
    private val appPreferences: AppPreferences
) : SmsRepository, RemoteDataSource() {

    override suspend fun sendMessage(sms: SmsMessage): Flow<Resource<SendMessageResponse>> {
        val botToken: String = appPreferences.getToken()
        val chatId: String = appPreferences.getChatId()

        return sendTelegramMessage(botToken = botToken, chatId = chatId) {
            val textMessage = createTextMessageFromSms(sms)
            val sendMessageRequest = SendMessageRequest(chatId = chatId, text = textMessage)

            telegramApi.sendMessage(
                token = botToken,
                sendMessageRequest = sendMessageRequest
            )
        }
    }

    private fun createTextMessageFromSms(sms: SmsMessage): String {
        return StringBuilder().apply {
            append("From: ").append(PhoneNumberUtils.formatNumber(sms.displayOriginatingAddress)).appendLine()
            append("Message: ").append(sms.displayMessageBody)
        }.toString()
    }
}