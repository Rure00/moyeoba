package com.moyeoba.moyeoba.data.dto.response

data class GetChatRoomsResponseDto (
    val roomIdList: List<Int>,
    val lastMsg: List<String>
)