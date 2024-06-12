package com.moyeoba.moyeoba.data.entity

import jakarta.persistence.*

@Entity @Table(name = "chatting_room_infos")
class ChattingRoomInfo(
    @OneToMany
    @JoinColumn(name = "user_id")
    val userId: List<User>,
    @OneToMany
    @JoinColumn(name = "user_id")
    val chatRoom: List<ChattingRoom>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
){

}