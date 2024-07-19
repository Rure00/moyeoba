package com.moyeoba.moyeoba.web_socket.encrypt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
class ChatIdEncryption {
    @Value("\${chat_id_key}")
    private lateinit var key: String

    fun encrypt(chatId: ChatId): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        val data = with(chatId) { "$userId,$nickname" }
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        return cipher.doFinal(data.toByteArray())
    }
    fun decrypt(data: ByteArray): ChatId {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val dataList = String(cipher.doFinal(data)).split(",")

        return ChatId(userId = dataList[0].toLong(), nickname = dataList[1])
    }
}