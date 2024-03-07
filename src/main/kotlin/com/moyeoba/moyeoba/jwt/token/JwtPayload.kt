package com.moyeoba.moyeoba.jwt.token

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*


class JwtPayload() {
    lateinit var uid: String
        private set
    var validTime: Long = 0
        private set
    lateinit var exp: String
        private set


    constructor(uid: String, validTime: Long) : this() {
        this.uid = uid
        this.validTime = validTime

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, validTime.toInt())
        val expiration: Date = Date(calendar.timeInMillis)

        exp = expiration.time.toString()
    }


    fun toJson(): String? {
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(this)
    }

    fun toJsonBase64(): String {
        val json = this.toJson()
        return Base64.getEncoder().encodeToString(json!!.toByteArray())
    }

    companion object {
        fun getFromJson(jsonStr: String): JwtPayload {
            val mapper = ObjectMapper()
            return mapper.readValue(jsonStr, JwtPayload::class.java)
        }
    }


}