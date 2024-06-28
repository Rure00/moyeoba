package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.ChattingMessage
import com.moyeoba.moyeoba.data.entity.fk.ChattingMessageId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional


@Repository
interface ChatMessageRepository: JpaRepository<ChattingMessage, ChattingMessageId> {
    fun findTopByIdChattingRoomIdOrderByCreatedAt(roomId: Long): Optional<ChattingMessage>
    fun findByIdChattingRoomIdOrderByCreatedAt(roomId: Long, pageable: Pageable): Page<ChattingMessage>
}