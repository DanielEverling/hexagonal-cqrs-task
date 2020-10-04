package com.cross.commons.extensions

import com.cross.domain.Notification
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.Optional

class NumberTest {

    private val expectedMessage = "expected message"

    @Test
    fun `should validate value is bigger than zero`() {
        val number = 10
        val hasOptional = number.valueBiggerThanZero(message = expectedMessage)
        hasOptional `should be equal to` Optional.empty()
    }

    @Test
    fun `should validate value is zero`() {
        val number = 0
        val hasOptional = number.valueBiggerThanZero(expectedMessage)
        hasOptional `should be equal to` Optional.of(Notification(notification = expectedMessage))
    }

}