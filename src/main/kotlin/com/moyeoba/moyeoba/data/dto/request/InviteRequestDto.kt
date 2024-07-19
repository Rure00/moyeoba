package com.moyeoba.moyeoba.data.dto.request

data class InviteRequestDto(
    val roomId: Long,
    val userIdList: List<Long>
)
