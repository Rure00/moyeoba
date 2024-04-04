package com.moyeoba.moyeoba.jwt.token

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*


class JwtHeader {
    val alg = "HS256" // 암호화 알고리즘
    val typ = "JWT" // 토큰의 유형. jwt 고정

    fun toJson(): String {
        val mapper = ObjectMapper()
        val headJson = mapper.writeValueAsString(this)
        return headJson
    }

    fun toJsonBase64(): String {
        val json = this.toJson()
        return Base64.getEncoder().encodeToString(json.toByteArray())
    }

    companion object {
        fun getFromJson(jsonStr: String): JwtHeader {
            val mapper = ObjectMapper()
            return mapper.readValue(jsonStr, JwtHeader::class.java)
        }
    }

}