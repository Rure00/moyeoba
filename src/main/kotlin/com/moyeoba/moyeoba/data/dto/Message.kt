package com.moyeoba.moyeoba.data.dto

import com.fasterxml.jackson.databind.util.JSONPObject
import kotlinx.serialization.Serializable
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders

@Serializable
data class Message(
    var type: String,
    var sender: String,
    var receiver: String,
    var data: String,
){
    constructor(sender: String, receiver: String):
            this("", sender = sender,receiver = receiver, "")
    constructor(): this(
        "", sender = "",receiver = "", ""
    )


}
