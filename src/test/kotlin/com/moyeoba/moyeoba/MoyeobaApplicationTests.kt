package com.moyeoba.moyeoba

import com.moyeoba.moyeoba.jwt.TokenManager
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MoyeobaApplicationTests {

	@Autowired
	private lateinit var tokenManager: TokenManager

	@Test
	fun contextLoads() {
		println("Hello world")
	}

	@Test
	fun tokenTest() {
		val pair = tokenManager.generateTokens(12L)
		println("Token is Generated: ${pair.accessToken}")

		val flag = tokenManager.validateToken(pair.accessToken)
		println("Validating Token: $flag")

		val id = tokenManager.getUserIdFromToken(pair.accessToken)
		println("Token id is $id")

	}

}
