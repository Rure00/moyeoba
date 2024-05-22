package com.moyeoba.moyeoba.web_socket

import com.fasterxml.jackson.databind.ObjectMapper
import com.moyeoba.moyeoba.data.chat.UserMessage


object Utils {
    private val objectMapper = ObjectMapper()

    @Throws(Exception::class)
    fun getObject(message: String?): UserMessage {
        return objectMapper.readValue(message, UserMessage::class.java)
    }

    @Throws(Exception::class)
    fun getString(userMessage: UserMessage): String {
        return objectMapper.writeValueAsString(userMessage)
    }
}