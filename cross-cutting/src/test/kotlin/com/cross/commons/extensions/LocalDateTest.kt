package com.cross.commons.extensions

import com.cross.domain.Notification
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.Date
import java.util.Optional

class LocalDateTest {

    private val expectedMessage = "expected message"

    @Test
    fun `should validate data in the future`() {
        val string = "some value"
        val hasOptional = LocalDate.now().plusMonths(1).isFuture(string)
        hasOptional `should be equal to` Optional.of(Notification(string))
    }

    @Test
    fun `should validate data in the past`() {
        val string = "some value"
        val hasOptional = LocalDate.now().minusDays(1).isFuture(string)
        hasOptional `should be equal to` Optional.empty()
    }

    @Test
    fun `should convert local date to date`() {
        val date: Date = LocalDate.now().toDate()
        date `should not be` null
    }
}