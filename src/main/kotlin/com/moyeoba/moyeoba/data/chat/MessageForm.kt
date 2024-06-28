package com.moyeoba.moyeoba.data.chat

import org.threeten.bp.LocalDateTime

class MessageForm(
    id: Long,
    roomId: Long,
    title: String,
    body: String,
    sender: String,
    date: LocalDateTime,
    var address: String
) {
    var data = MessageData(
        id, roomId, title, body, sender, date
    )

    fun toJson() = "{" +
            "\"message\":{" +
            "\"token\":\"$address\"," +
            "\"data\":{" +
            "\"id\":\"${data.id}\"," +
            "\"roomId\":\"${data.roomId}\"," +
            "\"title\":\"${data.title}\"," +
            "\"body\":\"${data.body}\"," +
            "\"sender\":\"${data.sender}\"," +
            "\"date\":\"${data.date}\"" +
            "}}}"

    data class MessageData(
        var id: Long,
        var roomId: Long,
        var title: String,
        var body: String,
        var sender: String,
        var date: LocalDateTime
    )
}
