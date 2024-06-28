package com.moyeoba.moyeoba.web_socket

import com.fasterxml.jackson.databind.ObjectMapper
import com.moyeoba.moyeoba.data.chat.RawMessage


object Utils {
    private val objectMapper = ObjectMapper()

    @Throws(Exception::class)
    fun getObject(message: String?): RawMessage {
        return objectMapper.readValue(message, RawMessage::class.java)
    }

    @Throws(Exception::class)
    fun getString(rawMessage: RawMessage): String {
        return objectMapper.writeValueAsString(rawMessage)
    }
}