package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.UserChattingRoom
import com.moyeoba.moyeoba.data.entity.fk.UserChattingRoomId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserChatRoomRepository: JpaRepository<UserChattingRoom, UserChattingRoomId> {
}