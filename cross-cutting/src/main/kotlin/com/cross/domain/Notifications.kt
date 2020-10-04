package com.cross.domain

import java.util.Optional

enum class NotificationType {
    INVALID_FIELD,
    DATA_INTEGRITY,
    INTEGRATION_SYSTEM,
    INTERNAL_SERVER_ERROR,
}

data class Notification(val notification: String, val type: NotificationType = NotificationType.INVALID_FIELD) {
    constructor(field: String? = null, notification: String, type: NotificationType = NotificationType.INVALID_FIELD): this(notification, type)
}

public fun List<Optional<Notification>>.toListNotification(): List<Notification> {
    val destination: MutableCollection<Optional<Notification>> = mutableListOf()
    return this.filter { it.isPresent }
            .toCollection(destination)
            .map { it.get() }
}
