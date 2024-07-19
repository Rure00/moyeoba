package com.moyeoba.moyeoba.service

import com.moyeoba.moyeoba.data.dto.response.GetChatRoomsResponseDto

interface ChatRoomService {
    fun createChatRoom(roomName: String, userIdList: List<Long>): Boolean
    fun invite(roomId: Long, userIdList: List<Long>): Boolean
    fun exit(roomId: Long, userId: Long): Boolean
    fun getRooms(userId: Long): GetChatRoomsResponseDto
}