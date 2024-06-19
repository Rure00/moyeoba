package com.moyeoba.moyeoba.data.entity

import com.moyeoba.moyeoba.data.entity.fk.UserChattingRoomId
import jakarta.persistence.*

@Entity @Table(name = "user_chatting_room")
class UserChattingRoom(
    @ManyToOne(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY,optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY, optional = false)
    @MapsId("chattingRoomId")
    @JoinColumn(name = "chatting_room_id")
    val chattingRoom: ChattingRoom,
){
    @EmbeddedId
    val id = UserChattingRoomId(user.id!!, chattingRoom.id!!)
}