package com.cuneytayyildiz.smstotelegram.data.remote.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
data class SendMessageResponse(
    @SerializedName("ok")
    val ok: Boolean,
    @SerializedName("result")
    val result: Result
) {
    data class Result(
        @SerializedName("chat")
        val chat: Chat,
        @SerializedName("date")
        val date: Int,
        @SerializedName("from")
        val from: From,
        @SerializedName("message_id")
        val messageId: Int,
        @SerializedName("text")
        val text: String
    ) {
        data class Chat(
            @SerializedName("all_members_are_administrators")
            val allMembersAreAdministrators: Boolean,
            @SerializedName("id")
            val id: Int,
            @SerializedName("title")
            val title: String,
            @SerializedName("type")
            val type: String
        )

        data class From(
            @SerializedName("first_name")
            val firstName: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("is_bot")
            val isBot: Boolean,
            @SerializedName("username")
            val username: String
        )
    }
}

