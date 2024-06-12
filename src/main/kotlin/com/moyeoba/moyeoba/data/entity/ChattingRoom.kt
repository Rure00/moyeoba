package com.moyeoba.moyeoba.data.entity

import jakarta.persistence.*


@Entity @Table(name = "chatting_rooms")
class ChattingRoom(
    var name: String,
    var userTokens: MutableList<String>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

}