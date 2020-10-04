package com.cross.events.task

import java.time.LocalDateTime
import java.util.*

data class ChangedAgentTaskEvent(val id: UUID, val data: LocalDateTime, val agent: String)