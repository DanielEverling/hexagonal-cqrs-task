package com.cross.commons.extensions

import com.cross.domain.Notification
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField
import java.util.Date
import java.util.Optional

fun ptBrLocalDate() = DateTimeFormatterBuilder()
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendLiteral('/')
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendLiteral('/')
        .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
        .toFormatter()

fun LocalDateTime.toPtBrLocalDate() : String = ptBrLocalDate().format(this)

fun LocalDate.toDate() : Date = Date.from(this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())

fun LocalDate.isFuture(message: String):  Optional<Notification> {
    return when (this.isAfter(LocalDate.now())) {
        true -> Optional.of(Notification(message))
        false -> Optional.empty()
    }
}