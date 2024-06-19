package com.moyeoba.moyeoba.dao.impl

import com.moyeoba.moyeoba.dao.ChatRoomDao
import com.moyeoba.moyeoba.data.entity.ChattingRoom
import com.moyeoba.moyeoba.repository.ChatRoomRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class ChatRoomDaoImpl: ChatRoomDao {
    @Autowired
    private lateinit var chatRoomRepository: ChatRoomRepository

    override fun create(roomName: String, tokenList: List<String>): Long? {
        val newRoom = ChattingRoom(
            name = roomName
        )

        try {
            chatRoomRepository.save(newRoom)
        } catch (e: Exception) {
            logger.error { e.toString() }
            return null
        }

        return newRoom.id
    }

    override fun invite(roomId: Long, tokens: List<String>) {
        val roomOptional = chatRoomRepository.findById(roomId)
        if(!roomOptional.isPresent) throw Exception("There is a no Room id is $roomId")

        val room = roomOptional.get()
        room.userTokens.addAll(tokens)
    }

    override fun delete(roomId: Long, token: String) {
        val roomOptional = chatRoomRepository.findById(roomId)
        if(!roomOptional.isPresent) throw Exception("There is a no Room id is $roomId")

        val room = roomOptional.get()
        room.userTokens.remove(token)
    }

    override fun getRoomsByUserId(userId: Long): List<ChattingRoom> {
        TODO("Not yet implemented")
    }
}