package com.moyeoba.moyeoba.data.entity.fk

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import java.io.Serializable

class ChattingMessageId(
    var chattingRoomId: Long,
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
): Serializable {
    constructor(): this(-1, -1)
}