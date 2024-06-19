package com.moyeoba.moyeoba.service.impl

import com.google.firebase.messaging.FirebaseMessaging
import com.moyeoba.moyeoba.dao.ChatMessageDao
import com.moyeoba.moyeoba.dao.ChatRoomDao
import com.moyeoba.moyeoba.dao.UserDao
import com.moyeoba.moyeoba.data.dto.response.GetChatRoomsResponseDto
import com.moyeoba.moyeoba.service.ChatRoomService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class ChatRoomServiceImpl: ChatRoomService {
    @Autowired
    private lateinit var chatRoomDao: ChatRoomDao
    @Autowired
    private lateinit var userDao: UserDao
    @Autowired
    private lateinit var chatMessageDao: ChatMessageDao

    override fun createChatRoom(roomName: String, userIdList: List<Long>): Boolean {
        val tokens = userDao.findTokens(userIdList)
        try {
            val newRoomId = chatRoomDao.create(roomName, tokens)!!
            val subscribeResult = FirebaseMessaging.getInstance().subscribeToTopic(tokens, newRoomId.toString())

            logger.info {
                "${subscribeResult.successCount}'s Devices success to subscribe a topic: ${newRoomId}"
                if(subscribeResult.failureCount > 0) {
                    "${subscribeResult.failureCount}'s Devices fail to subscribe a topic: ${newRoomId}"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    override fun invite(roomId: Long, userIdList: List<Long>): Boolean {
        try {
            val tokens = userDao.findTokens(userIdList)
            chatRoomDao.invite(roomId, tokens)

            val subscribeResult = FirebaseMessaging.getInstance().subscribeToTopic(tokens, roomId.toString())
            logger.info {
                "${subscribeResult.successCount}'s Devices success to subscribe a topic: $roomId"
                if(subscribeResult.failureCount > 0) {
                    "${subscribeResult.failureCount}'s Devices fail to subscribe a topic: $roomId"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    override fun exit(roomId: Long, userId: Long): Boolean {
        try {
            val tokens = userDao.findTokens(listOf(userId))
            chatRoomDao.delete(roomId, tokens[0])

            val subscribeResult = FirebaseMessaging.getInstance().unsubscribeFromTopic(tokens, roomId.toString())
            logger.info {
                "${subscribeResult.successCount}'s Devices success to unsubscribe a topic: $roomId"
                if(subscribeResult.failureCount > 0) {
                    "${subscribeResult.failureCount}'s Devices fail to unsubscribe a topic: $roomId"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    override fun getRooms(userId: Long): GetChatRoomsResponseDto {
        val result = GetChatRoomsResponseDto(
            mutableListOf(), mutableListOf()
        )

        try {
            val roomIdList = mutableListOf<Long>()
            val lastMsgList = mutableListOf<String>()

            chatRoomDao.getRoomsByUserId(userId).forEach {
                roomIdList.add(it.id!!)
                lastMsgList.add(chatMessageDao.getLastMessageBody(it.id))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }


}