package com.moyeoba.moyeoba.data.dto.request

data class CreateChatRoomRequestDto (
    var roomName: String,
    val userId: Long,
    val userList: MutableList<Long>
)