package com.cross.events.task

import java.time.LocalDateTime
import java.util.*

data class InitializeTaskEvent (val id: UUID, val data: LocalDateTime)