package com.hexagonal.task.infra.query.task

import java.time.LocalDateTime
import java.util.*

data class TaskLateProjection(val id: UUID,
                              val expectedExecution: LocalDateTime)