package com.hexagonal.taskschedule.events

import com.cross.events.task.CreatedTaskEvent
import com.cross.logs.logger
import org.springframework.context.event.EventListener

class CreatedTaskEventHandler {

    private val logger = logger()

    @EventListener
    fun eventHandler(createdTaskEvent: CreatedTaskEvent) {
        TODO("Not yet implemented")
    }

}