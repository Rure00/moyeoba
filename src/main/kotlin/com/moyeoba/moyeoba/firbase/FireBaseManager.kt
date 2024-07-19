package com.moyeoba.moyeoba.firbase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import java.io.FileInputStream

private val logger = KotlinLogging.logger {}

class FireBaseManager {
    @Value("\${firebase_sdk_dir}")
    private lateinit var firebaseSdkPath: String

    private val fireBaseInstance: FirebaseMessaging by lazy {
        val serviceAccount = FileInputStream(firebaseSdkPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
        FirebaseMessaging.getInstance()
    }

    fun sendToSingleDevice(targetToken: String, dto: ChatResponseDto) {
        val message = Message.builder()
            .putAllData(dto.toMap())
            .setToken(targetToken)
            .build()


        try {
            fireBaseInstance.send(message)
            logger.info { "FirebaseManager:sendToSingleDevice) Successfully sent message" }
        } catch (exception: FirebaseMessagingException) {
            logger.error { "FirebaseManager:sendToSingleDevice: $exception" }
        }
    }

    fun sendToTopic(topicUrl: String, dto: ChatResponseDto) {
        val message = Message.builder()
            .putAllData(dto.toMap())
            .setTopic(topicUrl)
            .build()

        try {
            fireBaseInstance.send(message)
            logger.info { "FirebaseManager:sendToTopic) Successfully sent message" }
        } catch (exception: FirebaseMessagingException) {
            logger.error { "FirebaseManager:sendToTopic: $exception" }
        }
    }
}