package com.moyeoba.moyeoba.data.dto

import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders

data class Message(
    var type: String,
    var sender: String,
    var receiver: String,
    var data: String,
) {
    constructor(sender: String, receiver: String):
            this("", sender = sender,receiver = receiver, "")
    constructor(): this(
        "", sender = "",receiver = "", ""
    )
}
