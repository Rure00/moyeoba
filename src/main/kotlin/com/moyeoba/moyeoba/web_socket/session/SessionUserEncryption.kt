package com.moyeoba.moyeoba.web_socket.session

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
class SessionUserEncryption {
    @Value("\${chat_id_key}")
    private lateinit var key: String

    fun encrypt(sessionId: String, random: Int): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        val data = "$sessionId:$random"
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        return cipher.doFinal(data.toByteArray())
    }
    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val dataList = String(cipher.doFinal(data.toByteArray())).split(":")

        return "${dataList[0]}:${dataList[1]}"
    }
}