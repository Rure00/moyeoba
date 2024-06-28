package com.moyeoba.moyeoba.dao.impl

import com.moyeoba.moyeoba.dao.ChattingRoomDao
import com.moyeoba.moyeoba.data.entity.ChattingRoom
import com.moyeoba.moyeoba.repository.ChattingRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChattingRoomDaoImpl: ChattingRoomDao {
    @Autowired
    private lateinit var chattingRoomRepository: ChattingRoomRepository

    override fun getChattingRoom(roomId: Long): ChattingRoom?
        = chattingRoomRepository.findById(roomId).get()

    override fun save(chattingRoom: ChattingRoom): Boolean {
        try {
            val saved = chattingRoomRepository.save(chattingRoom)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }


}