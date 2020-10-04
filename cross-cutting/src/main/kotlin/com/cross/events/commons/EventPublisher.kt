package com.cross.events.commons

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisher (private val eventPublisher: ApplicationEventPublisher) {

    fun publisher(any: Any) {
        eventPublisher.publishEvent(any)
    }

}