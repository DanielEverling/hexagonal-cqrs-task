package com.cross.events.commons

import com.cross.logs.logger
import com.google.gson.Gson
import org.springframework.context.event.EventListener
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletResponse

const val CONTENT_TYPE = "Content-Type"

@Component
class EntityNotFoundEventHandler (val response : HttpServletResponse) {

    private val logger = logger();

    @EventListener
    fun eventHandler(domainInvalidEvent: DomainInvalidEvent) {
        val notifications = Gson().toJson(domainInvalidEvent.notifications)
        logger.info("There were the following errors creating the entity $notifications")
        val out = response.writer
        response.reset()
        response.status = HttpStatus.BAD_GATEWAY.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        out.print(notifications)
        out.flush()
        out.close()
    }

}