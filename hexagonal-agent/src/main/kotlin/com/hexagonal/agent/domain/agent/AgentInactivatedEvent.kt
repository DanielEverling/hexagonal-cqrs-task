package com.hexagonal.agent.domain.agent

import com.cross.events.commons.DomainEvent
import java.time.LocalDateTime
import java.util.*

data class AgentInactivatedEvent (val id: UUID, val name: String, val date: LocalDateTime) : DomainEvent