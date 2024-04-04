package com.moyeoba.moyeoba.jwt.token

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*


class JwtSignature() {
    lateinit var verification: String
        private set
    lateinit var secretKey: String
        private set

    constructor( header: JwtHeader, payload: JwtPayload, secretKey: String): this() {
        val sum = header.toJsonBase64() + "." + payload.toJsonBase64()

        this.verification = Base64.getEncoder().encodeToString(sum.toByteArray())
        this.secretKey = secretKey
    }

    fun toJsonBase64(): String {
        val mapper = ObjectMapper()
        val json: String = mapper.writeValueAsString(this)
        return Base64.getEncoder().encodeToString(json.toByteArray())
    }

    fun checkToken(header: JwtHeader, payload: JwtPayload): Boolean {
        val sum = header.toJsonBase64() + "." + payload.toJsonBase64()
        val vrf: String = Base64.getEncoder().encodeToString(sum.toByteArray())

        return vrf == verification
    }

    companion object {
        fun getFromJson(jsonStr: String): JwtSignature {
            val mapper = ObjectMapper()
            return  mapper.readValue(jsonStr, JwtSignature::class.java)
        }
    }

}