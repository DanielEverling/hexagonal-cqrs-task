package com.cross.events.task

import com.cross.domain.vo.Address
import java.time.LocalDateTime
import java.util.*

data class CreatedTaskEvent(val id: UUID, val data: LocalDateTime, val agent: String, val activity: String, val local: Address)