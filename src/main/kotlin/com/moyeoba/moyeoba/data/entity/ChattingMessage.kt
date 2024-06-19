package com.moyeoba.moyeoba.data.entity

import com.moyeoba.moyeoba.data.entity.fk.ChattingMessageId
import com.moyeoba.moyeoba.data.entity.fk.UserChattingRoomId
import jakarta.persistence.*
import net.bytebuddy.dynamic.loading.InjectionClassLoader.Strategy
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "chatting_messages")
class ChattingMessage(
    @ManyToOne(cascade = [CascadeType.REMOVE])
    var chattingRoomId: ChattingRoom,

    var userId: Long,
    var userName: String,
    var body: String,
) {
    @EmbeddedId
    val id = ChattingMessageId(chattingRoomId.id!!)

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
}
