package com.moyeoba.moyeoba.api.kakao

import com.fasterxml.jackson.databind.ObjectMapper
import com.moyeoba.moyeoba.api.SocialLoginFlag
import com.moyeoba.moyeoba.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import kotlin.jvm.optionals.getOrNull

@Component
class KakaoApiManager {
    @Value("\${kakao_client_id}")
    private lateinit var kakaoClientId: String

    @Value("\${kakao_client_secret}")
    private lateinit var kakaoClientSecret: String

    @Autowired
    private lateinit var userRepository: UserRepository

    //TODO: URL 고치기
    private val redirectUrl = "http://localhost:8080/login/callback/kakao"


    fun authorize(type: String, payload: String): SocialLoginFlag {
        val token = if(type == "code")
                        getToken(payload)?.access_token
                    else payload
        if(token.isNullOrEmpty()) return SocialLoginFlag.Error

        val user = getUserProfile(token)?.let {
            userRepository.findByKakaoId(it.id).getOrNull()
        }

        return if(user!=null) SocialLoginFlag.Found
            else SocialLoginFlag.NotFound
    }

    private fun getToken(payload: String): KakaoTokenDto? {
        val template = RestTemplate()

        val kakaoTokenRequestHeaders: HttpHeaders = HttpHeaders()
        kakaoTokenRequestHeaders.add("Content-type", "application/x-www-form-urlencoded")

        val params: MultiValueMap<String, String?> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", kakaoClientId)

        //TODO: 카카오 보안 강화 켜야함.
        //params.add("client_secret", kakaoClientSecret);

        params.add("code", payload)
        params.add("redirect_uri", redirectUrl)

        val kakaoTokenRequest = HttpEntity(params, kakaoTokenRequestHeaders)
        val oauthTokenResponse = template.exchange<String>(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String::class.java
        )
        println(oauthTokenResponse)

        return try {
            ObjectMapper().readValue(oauthTokenResponse.body, KakaoTokenDto::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun getUserProfile(kakaoToken: String): KakaoProfileDto? {
        // 토큰을 이용해 정보를 받아올 API 요청을 보낼 로직 작성하기
        val template = RestTemplate()
        val userDetailReqHeaders: HttpHeaders = HttpHeaders()

        userDetailReqHeaders.add("Authorization", "Bearer $kakaoToken")
        userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8")
        val kakaoProfileRequest = HttpEntity<MultiValueMap<String, String>>(userDetailReqHeaders)

        val userDetailResponse = template.exchange<String>(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String::class.java
        )
        println(userDetailResponse)

        return try {
            ObjectMapper().readValue(userDetailResponse.body, KakaoProfileDto::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}