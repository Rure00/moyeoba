package com.moyeoba.moyeoba

import com.moyeoba.moyeoba.data.chat.MessageTarget
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

//TODO: exclude 부분 삭제하기
@SpringBootApplication(/*exclude = [SecurityAutoConfiguration::class]*/)
class MoyeobaApplication
fun main(args: Array<String>) {
	runApplication<MoyeobaApplication>(*args)
}
