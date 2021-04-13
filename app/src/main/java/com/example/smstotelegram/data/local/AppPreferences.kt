package com.example.smstotelegram.data.local

import android.content.SharedPreferences
import javax.inject.Inject


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */

interface PreferenceStorage {
    fun setChatId(chatId: String)

    fun setToken(token: String)

    fun getChatId(): String

    fun getToken(): String
}

class AppPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PreferenceStorage {

    private val KEY_CHAT_ID: String = "KEY_CHAT_ID"
    private val KEY_BOT_TOKEN: String = "KEY_BOT_TOKEN"

    override fun setChatId(chatId: String) {
        sharedPreferences.edit().putString(KEY_CHAT_ID, chatId).apply()
    }

    override fun setToken(token: String) {
        sharedPreferences.edit().putString(KEY_BOT_TOKEN, token).apply()
    }

    override fun getChatId(): String {
        return sharedPreferences.getString(KEY_CHAT_ID, null).orEmpty()
    }

    override fun getToken(): String {
        return sharedPreferences.getString(KEY_BOT_TOKEN, null).orEmpty()
    }

}