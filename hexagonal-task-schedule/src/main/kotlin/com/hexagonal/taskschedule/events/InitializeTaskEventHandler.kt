package com.hexagonal.taskschedule.events

import com.cross.events.task.InitializeTaskEvent
import com.cross.logs.logger
import org.springframework.context.event.EventListener

class InitializeTaskEventHandler {

    private val logger = logger()

    @EventListener
    fun eventHandler(initializeTaskEvent: InitializeTaskEvent) {
        TODO("Not yet implemented")
    }

}