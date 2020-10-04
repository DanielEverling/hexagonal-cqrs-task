package com.cross.commands

import com.cross.events.commons.EventPublisher
import org.springframework.beans.factory.annotation.Autowired

open abstract class BaseCommandHandler<T : BaseCommand> {

    @Autowired
    open lateinit var eventPublisher: EventPublisher

    abstract fun handler(command : T)

}