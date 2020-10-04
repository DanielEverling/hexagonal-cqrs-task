package com.cross.domain.vo

import com.cross.domain.Notification
import com.cross.commons.extensions.format
import java.math.BigDecimal
import java.util.*

@Suppress("ConstructorParameterNaming")
data class Currency constructor(private val _amount: BigDecimal) {

    companion object {
        inline fun of(amount: BigDecimal) = Currency(_amount = amount)

        inline fun of(amount: Double) = Currency(_amount = BigDecimal(amount))

        inline fun of(amount: Int) = Currency(_amount = BigDecimal(amount))

        inline fun zero() = Currency(_amount = BigDecimal.ZERO)
    }

    operator fun times(amount: Double) = of(amount = _amount.multiply(BigDecimal(amount)))

    operator fun times(amount: Int) = of(amount = _amount.multiply(BigDecimal(amount)))

    operator fun times(amount: Quantity) = of(amount = _amount.multiply(BigDecimal(amount.value)))

    operator fun minus(amount: Double) = of(amount = _amount.subtract(BigDecimal(amount)))

    operator fun minus(amount: BigDecimal) = of(amount = _amount.subtract(amount))

    operator fun minus(currency: Currency) = of(amount = _amount.subtract(currency._amount))

    operator fun plus(amount: Double) = of(amount = _amount.plus(BigDecimal(amount)))

    operator fun plus(amount: BigDecimal) = of(amount = _amount.plus(amount))

    operator fun plus(currency: Currency) = of(amount = _amount.plus(currency._amount))

    val amount: BigDecimal
        get() = _amount

    val amountFormatted: String
        get() = amount.format()

    fun valueBiggerThanZero(message : String) : Optional<Notification> {
        return when (_amount > BigDecimal.ZERO) {
            true -> Optional.empty()
            else -> Optional.of(Notification(notification = message))
        }
    }
}
