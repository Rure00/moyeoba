package com.moyeoba.moyeoba.dao

import com.moyeoba.moyeoba.data.entity.ChattingRoom

interface ChatRoomDao {
    fun create(roomName:String, tokenList: List<String>): Long?
    fun invite(roomId: Long, tokens: List<String>)
    fun delete(roomId: Long, token: String)
    fun getRoomsByUserId(userId: Long): List<ChattingRoom>
}