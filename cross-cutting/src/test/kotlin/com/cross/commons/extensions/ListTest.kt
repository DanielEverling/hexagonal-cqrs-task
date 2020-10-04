package com.cross.commons.extensions

import com.cross.domain.Notification
import com.cross.domain.vo.Currency
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.Optional

class ListTest {

    @Test
    fun `should sum a list of currency values`() {
        val expectedValue = Currency.of(BigDecimal.valueOf(6514.207162))
        val values = listOf(
                Currency.of(1000),
                Currency.Companion.of(1983.07),
                Currency.Companion.of(BigDecimal.valueOf(3531.137162))
        )

        val sumValue = values.sumByCurrency { it }

        sumValue.amount.toSixDecimalPlaces() `should be equal to` expectedValue.amount
    }

    @Test
    fun `should validate if list has value and not return notification`() {
        val list = listOf(1,2,3)
        list.noHasValue("Some message") `should be equal to` Optional.empty()
    }

    @Test
    fun `should validate if list has value and return notification`() {
        val list = emptyList<Any>()
        list.noHasValue("Some message") `should be equal to` Optional.of(Notification(notification = "Some message"))
    }

}