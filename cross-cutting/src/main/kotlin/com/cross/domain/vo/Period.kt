package com.cross.domain.vo

import com.cross.domain.Notification
import com.cross.domain.ValidatorsAware
import java.time.LocalDateTime
import java.util.*

data class Period(val start: LocalDateTime, val end: LocalDateTime): ValidatorsAware {

    override fun validators(): List<Optional<Notification>> {
       return emptyList()
    }

}