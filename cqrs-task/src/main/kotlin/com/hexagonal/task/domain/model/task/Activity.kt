package com.hexagonal.task.domain.model.task

import com.cross.commons.extensions.isBlank
import com.cross.commons.extensions.isFuture
import com.cross.domain.Entity
import com.cross.domain.Notification
import com.cross.domain.NotificationType
import com.cross.infra.BIRTHDAY_INVALID
import com.cross.infra.FIELD_REQUIRED
import com.cross.infra.MessageBundle
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

enum class ActivityStatus {
    WAITING,
    STARTED,
    READY,
    CANCELED
}

data class Activity(val description: String, val date: LocalDateTime): Entity() {

    private var _status: ActivityStatus = ActivityStatus.WAITING

    var initializeData: LocalDateTime? = null

    override fun validators() = listOf (
            description.isBlank(MessageBundle.message(FIELD_REQUIRED, "Descrição"))
    )

    val status: ActivityStatus
        get() = _status

    fun initialize(): List<Optional<Notification>> {
        if (this.status == ActivityStatus.WAITING) {
            this._status = ActivityStatus.STARTED
            this.initializeData = LocalDateTime.now()
            return emptyList()
        }
        return listOf(Optional.of(Notification("Atividade não pode ser inicializada pois está ${this.status}", NotificationType.BUSINESS_RULE)))
    }

    fun cancel() {
        if (this.status == ActivityStatus.READY) {
            return
        }
        this._status = ActivityStatus.CANCELED
    }

    fun ready() {
        if (this.status == ActivityStatus.CANCELED) {
            return
        }
        this._status = ActivityStatus.READY
    }

}