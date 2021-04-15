package com.example.smstotelegram.data.local

import android.content.SharedPreferences
import javax.inject.Inject


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */

interface PreferenceStorage {
    fun isIntroShown(): Boolean

    fun setIntroShown()

    fun setChatId(chatId: String)

    fun setTokenId(token: String)

    fun getChatId(): String

    fun getTokenId(): String
}

class AppPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PreferenceStorage {

    private val KEY_INTRO_SHOWN: String = "KEY_INTRO_SHOWN"
    private val KEY_CHAT_ID: String = "KEY_CHAT_ID"
    private val KEY_BOT_TOKEN: String = "KEY_BOT_TOKEN"

    override fun isIntroShown(): Boolean {
        return sharedPreferences.getBoolean(KEY_INTRO_SHOWN, false)
    }

    override fun setIntroShown() {
        sharedPreferences.edit().putBoolean(KEY_INTRO_SHOWN, true).apply()
    }

    override fun setChatId(chatId: String) {
        sharedPreferences.edit().putString(KEY_CHAT_ID, chatId).apply()
    }

    override fun setTokenId(token: String) {
        sharedPreferences.edit().putString(KEY_BOT_TOKEN, token).apply()
    }

    override fun getChatId(): String {
        return sharedPreferences.getString(KEY_CHAT_ID, null).orEmpty()
    }

    override fun getTokenId(): String {
        return sharedPreferences.getString(KEY_BOT_TOKEN, null).orEmpty()
    }

}