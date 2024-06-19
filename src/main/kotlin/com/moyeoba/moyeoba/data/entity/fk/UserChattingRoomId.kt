package com.moyeoba.moyeoba.data.entity.fk

import jakarta.persistence.Column
import java.io.Serializable


class UserChattingRoomId(
    @Column(name = "chatting_room_id")
    var chattingRoomId: Long,
    @Column(name = "user_id")
    var userId: Long
): Serializable {
    constructor(): this(-1, -1)
}