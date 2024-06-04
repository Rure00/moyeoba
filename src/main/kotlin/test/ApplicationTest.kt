package test

import com.moyeoba.moyeoba.data.chat.MessageForm
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.threeten.bp.LocalDateTime

@SpringBootTest
class ApplicationTest {
    @Test
    fun toJson() {
        val msg = MessageForm(
            0L, 1L, "title", "body", "sender", LocalDateTime.now(), "address"
        )

        println(msg.toJson())
    }
}