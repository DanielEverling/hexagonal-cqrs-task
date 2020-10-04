package com.cross.domain.vo

import com.cross.domain.Notification
import org.amshove.kluent.`should be equal to`
import java.math.BigDecimal
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.Optional

internal class CurrencyTest {

    @TestFactory
    fun `create currency type `() = listOf(
        DynamicTest.dynamicTest("should create a currency type with int") {
            validateCurrency(Currency.of(1983), BigDecimal(1983))
        },
        DynamicTest.dynamicTest("should create a currency type with double") {
            validateCurrency(Currency.of(5123.98), BigDecimal(5123.98))
        },
        DynamicTest.dynamicTest("should create a currency type with big decimal") {
            validateCurrency(Currency.of(BigDecimal(67.2)), BigDecimal(67.2))
        }
    )

    @TestFactory
    fun `should subtract values`() = listOf(
        DynamicTest.dynamicTest("should subtract values with double") {
            (Currency.of(amount = 10.00) - 15.55).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(-5.55)).amountFormatted
        },
        DynamicTest.dynamicTest("should subtract values withbig decimal") {
            (Currency.of(amount = 10.00) - BigDecimal(15.55)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(-5.55)).amountFormatted
        },
        DynamicTest.dynamicTest("should subtract values with currency") {
            (Currency.of(amount = 10.00) - Currency.of(amount = 15.55)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(-5.55)).amountFormatted
        }
    )

    @TestFactory
    fun `should multiply values`() = listOf(
            DynamicTest.dynamicTest("should multiply values with double") {
                (Currency.of(amount = 10.00) * 10.00).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(100)).amountFormatted
            },
            DynamicTest.dynamicTest("should multiply values with int") {
                (Currency.of(amount = 10.00) * 12).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(120)).amountFormatted
            },
            DynamicTest.dynamicTest("should multiply values with quantity") {
                (Currency.of(amount = 10.00) * Quantity.of(5)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(50)).amountFormatted
            }
    )

    @TestFactory
    fun `should sum values`() = listOf(
            DynamicTest.dynamicTest("should sum values with double") {
                (Currency.of(amount = 10.00) + 10.00).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(20)).amountFormatted
            },
            DynamicTest.dynamicTest("should sum values with big decimal") {
                (Currency.of(amount = 10.00) + BigDecimal.valueOf(12)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(22)).amountFormatted
            },
            DynamicTest.dynamicTest("should multiply values with currency") {
                (Currency.of(amount = 10.00) + Currency.of(amount = 10.00)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(20)).amountFormatted
            }
    )

    @Test
    fun `should create a currency with zero value`() {
        validateCurrency(Currency.zero(), BigDecimal.ZERO)
    }

    @Test
    fun `should validate value bigger than zero`() {
        val currency = Currency.of(5)
        val hasNotification = currency.valueBiggerThanZero("message")
        hasNotification `should be equal to` Optional.empty()
    }

    @Test
    fun `should validate when value is zero`() {
        val currency = Currency.zero()
        val hasNotification = currency.valueBiggerThanZero("message")
        hasNotification `should be equal to` Optional.of(Notification(notification = "message"))
    }

    private fun validateCurrency(currencyToValidate: Currency, numberShouldBeEqual: Number) {
        currencyToValidate.amount `should be equal to` numberShouldBeEqual
    }
}
