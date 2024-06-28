package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.ChattingRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository: JpaRepository<ChattingRoom, Long> {

}