package com.moyeoba.moyeoba.web_socket

import com.fasterxml.jackson.databind.ObjectMapper
import com.moyeoba.moyeoba.data.dto.Message


object Utils {
    private val objectMapper = ObjectMapper()

    @Throws(Exception::class)
    fun getObject(message: String?): Message {
        return objectMapper.readValue(message, Message::class.java)
    }

    @Throws(Exception::class)
    fun getString(message: Message): String {
        return objectMapper.writeValueAsString(message)
    }
}