package com.moyeoba.moyeoba.firbase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.math.log


private val logger = KotlinLogging.logger {}

@Configuration
class FirebaseConfig {
    @Value("\${firebase_sdk_dir}")
    private lateinit var firebaseSdkPath: String

    @PostConstruct
    fun initialize() {
        try {
            val serviceAccount = FileInputStream(firebaseSdkPath)

            logger.info { "dir is $firebaseSdkPath" }

            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            FirebaseApp.initializeApp(options)
        } catch (e: FileNotFoundException) {
            logger.error {"Firebase ServiceAccountKey FileNotFoundException" + e.message}
        } catch (e: IOException) {
            logger.error{"FirebaseOptions IOException" + e.message}
        }
    }
}