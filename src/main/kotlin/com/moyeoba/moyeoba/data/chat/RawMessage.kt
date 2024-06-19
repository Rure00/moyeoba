package com.moyeoba.moyeoba.data.chat

import kotlinx.serialization.Serializable
import org.threeten.bp.LocalDateTime

@Serializable
data class RawMessage(
    var roomId: Long,
    var userId: Long,
    var userName: String,
    var topicUrl: String,
    var body: String,
    var sender: String,
    var date: String,
)
