package com.moyeoba.moyeoba.data.dto.response

data class GetChatRoomsResponseDto (
    val roomIdList: List<Long>,
    val lastMsg: List<String>
)