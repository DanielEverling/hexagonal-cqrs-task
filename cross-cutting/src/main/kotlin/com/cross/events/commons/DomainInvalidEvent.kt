package com.cross.events.commons

import com.cross.domain.Notification

data class DomainInvalidEvent(val notifications : List<Notification>)