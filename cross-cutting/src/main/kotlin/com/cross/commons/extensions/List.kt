package com.cross.commons.extensions

import com.cross.domain.Notification
import com.cross.domain.vo.Currency
import java.util.Optional

public inline fun <T> Iterable<T>.sumByCurrency(selector: (T) -> Currency): Currency {
    var sum: Currency = Currency.zero()
    this.forEach {
        sum += selector(it)
    }
    return sum
}

public inline fun <T> List<T>.noHasValue(message: String): Optional<Notification> {
    return when(this.isEmpty()) {
        true -> Optional.of(Notification(notification = message))
        false -> Optional.empty()
    }
}

public fun <T> Iterable<T>.firstOrOptional(): Optional<T> {
    when (this) {
        is List -> {
            return if (isEmpty())
                Optional.empty<T>()
            else
                Optional.of(this[0])
        }
        else -> {
            val iterator = iterator()
            if (!iterator.hasNext())
                return Optional.empty<T>()
            return Optional.of(iterator.next())
        }
    }
}