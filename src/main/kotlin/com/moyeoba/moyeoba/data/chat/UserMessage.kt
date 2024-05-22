package com.moyeoba.moyeoba.data.chat

import kotlinx.serialization.Serializable

@Serializable
data class UserMessage(
    var type: String,
    var sender: Long,
    var receiver: String,
    var data: String,
){
    constructor(sender: Long, receiver: String):
            this("", sender = sender,receiver = receiver, "")
    constructor(): this(
        "", sender = 0L,receiver = "", ""
    )


}
