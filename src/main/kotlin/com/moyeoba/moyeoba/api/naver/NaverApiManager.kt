package com.moyeoba.moyeoba.api.naver

import com.fasterxml.jackson.databind.ObjectMapper
import com.moyeoba.moyeoba.api.SocialLoginResult
import com.moyeoba.moyeoba.dao.UserDao
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
class NaverApiManager {

    @Autowired
    private lateinit var userDao: UserDao

    @Value("\${naver_client_id}")
    private lateinit var naverClientId: String

    @Value("\${naver_client_secret}")
    private lateinit var naverClientSecret: String

    fun authorize(type: String, payload: String): SocialLoginResult {
        val token = if(type == "code")
            getToken(payload)?.access_token
        else payload

        if(token.isNullOrEmpty()) return SocialLoginResult(-1, SocialLoginResult.LoginFlag.Error)

        val user = getUserProfile(token)?.let {
            userDao.getOrCreateNaver(it.response.id)
        }

        return if(userDao.getEmail(user!!.id!!).isNullOrEmpty()) {
            SocialLoginResult(user.id!!, SocialLoginResult.LoginFlag.NewUser)
        } else {
            SocialLoginResult(user.id!!, SocialLoginResult.LoginFlag.ExistingUser)
        }
    }

    private fun getToken(code: String): NaverTokenDto? {
        val state = "moyeoba"

        val template = RestTemplate()
        val naverTokenRequestHeaders: HttpHeaders = HttpHeaders()
        naverTokenRequestHeaders.add("Content-type", "application/x-www-form-urlencoded")

        val params: MultiValueMap<String, String?> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", naverClientId)
        params.add("client_secret", naverClientSecret)
        params.add("code", code)
        params.add("state", state)

        val naverTokenRequest = HttpEntity(params, naverTokenRequestHeaders)

        val oauthTokenResponse = template.exchange<String>(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naverTokenRequest,
                String::class.java
        )
        println(oauthTokenResponse)



        return try {
            ObjectMapper().readValue(oauthTokenResponse.body, NaverTokenDto::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


    private fun getUserProfile(naverToken: String): NaverProfileDto? {
        // 토큰을 이용해 정보를 받아올 API 요청을 보낼 로직 작성하기
        val template = RestTemplate()
        val userDetailReqHeaders: HttpHeaders = HttpHeaders()
        userDetailReqHeaders.add("Authorization", "Bearer $naverToken")
        //userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        val naverProfileRequest = HttpEntity<MultiValueMap<String, String>>(userDetailReqHeaders)

        val userDetailResponse = template.exchange<String>(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                naverProfileRequest,
                String::class.java
        )
        println(userDetailResponse)

        return try {
            ObjectMapper().readValue(userDetailResponse.body, NaverProfileDto::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}