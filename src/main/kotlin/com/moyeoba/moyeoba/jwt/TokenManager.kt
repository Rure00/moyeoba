package com.moyeoba.moyeoba.jwt

import com.moyeoba.moyeoba.jwt.token.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenManager {
    //https://velog.io/@hiy7030/Spring-Security-JWT-%EC%83%9D%EC%84%B1#2-%ED%85%8C%EC%8A%A4%ED%8C%85-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B5%AC%ED%98%84
    //https://hudi.blog/self-made-jwt/
    //TODO: TOKEN_KEY 설정하기
    private val TOKEN_HEADER_KEY = "Authorization"

    @Autowired
    private lateinit var userDetailsService: UserDetailsService
    @Value("\${jwt_secret_key}")
    private val secretKey: String? = null

    companion object {
        const val ACCESS_TOKEN_VALID_TIME: Long = 60*30     // 30분
        const val REFRESH_TOKEN_VALID_TIME: Long = (60 * 60 * 24) * 14 //2주
    }




    private fun generateAccessToken(id: Long): String {
        println("토큰 생성 중...")

        //STEP1) Header, Payload 생성
        val header = JwtHeader()
        val payload = JwtPayload(id.toString(), ACCESS_TOKEN_VALID_TIME)
        //STEP2) Sign 생성
        val sign = JwtSignature(header, payload, secretKey!!)
        //STEP3) 합치기
        val result: String = (header.toJsonBase64() + "." + payload.toJsonBase64()).toString() + "." + sign.toJsonBase64()

        return result
    }


    private fun generateRefreshToken(id: Long): String {
        //STEP1) Header, Payload 생성
        val header = JwtHeader()
        val payload = JwtPayload(id.toString(), REFRESH_TOKEN_VALID_TIME)
        //STEP2) Sign 생성
        val sign = JwtSignature(header, payload, secretKey!!)
        //STEP3) 합치기
        val result: String = (header.toJsonBase64() + "." + payload.toJsonBase64()).toString() + "." + sign.toJsonBase64()

        return result
    }

    fun getAuthentication(token: String): Authentication? {
        val userDetails = userDetailsService.loadUserByUsername(this.getUserIdFromToken(token))
        userDetails?.let {
            return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
        }

        return null
    }

    fun generateTokens(id: Long): TokenPair {
        return TokenPair(
                generateAccessToken(id), generateRefreshToken(id)
        )
    }

    fun validateToken(token: String): Boolean {
        val jsonArray = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        println("header:  " + String(Base64.getDecoder().decode(jsonArray[0])))
        val header: JwtHeader = JwtHeader.getFromJson(String(Base64.getDecoder().decode(jsonArray[0])))

        println("payload:  " + String(Base64.getDecoder().decode(jsonArray[1])))
        val payload: JwtPayload = JwtPayload.getFromJson(String(Base64.getDecoder().decode(jsonArray[1])))

        println("sign:  " + String(Base64.getDecoder().decode(jsonArray[2])))
        val sign: JwtSignature = JwtSignature.getFromJson(String(Base64.getDecoder().decode(jsonArray[2])))

        return sign.checkToken(header, payload)
    }


    fun getUserIdFromToken(token: String): String {
        val jsonArray = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val payload: JwtPayload = JwtPayload.getFromJson(String(Base64.getDecoder().decode(jsonArray[1])))

        return payload.uid
    }
}