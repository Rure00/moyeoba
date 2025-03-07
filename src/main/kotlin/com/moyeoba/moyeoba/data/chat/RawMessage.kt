package com.moyeoba.moyeoba.data.chat

import kotlinx.serialization.Serializable
import org.threeten.bp.LocalDateTime

@Serializable
data class RawMessage(
    var userName: String,
    var roomId: Long,
    var topicUrl: String,
    var body: String,
)
