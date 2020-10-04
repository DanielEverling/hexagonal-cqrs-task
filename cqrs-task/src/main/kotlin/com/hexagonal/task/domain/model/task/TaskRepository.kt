package com.hexagonal.task.domain.model.task

import java.util.*

interface TaskRepository {

    fun insert(task: Task)

    fun findById(id: UUID): Optional<Task>

    fun updateAgent(task: Task)
}