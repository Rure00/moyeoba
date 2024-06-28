package com.moyeoba.moyeoba.data.entity

import jakarta.persistence.*


@Entity @Table(name = "chatting_rooms")
class ChattingRoom(
    var name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    val userTokens = mutableSetOf<String>()

    @OneToMany(mappedBy = "chattingRoom", fetch = FetchType.EAGER)
    val users = mutableSetOf<UserChattingRoom>()

}