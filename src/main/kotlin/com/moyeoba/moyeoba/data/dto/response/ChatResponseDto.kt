package com.moyeoba.moyeoba.data.dto.response

data class ChatResponseDto(
    val id: Long,
    val roomId: Long,
    val topicUrl: String,
    val body: String,
    val sender: String,
    val date: String
) {
    fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["id"] = id.toString()
        map["roomId"] = roomId.toString()
        map["body"] = body
        map["sender"] = sender
        map["date"] = date

        return map
    }
}