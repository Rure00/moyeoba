package com.moyeoba.moyeoba.dao

import com.moyeoba.moyeoba.data.entity.ChattingRoom

interface ChattingRoomDao {
    fun getChattingRoom(roomId: Long): ChattingRoom?
    fun save(chattingRoom: ChattingRoom): Boolean
}