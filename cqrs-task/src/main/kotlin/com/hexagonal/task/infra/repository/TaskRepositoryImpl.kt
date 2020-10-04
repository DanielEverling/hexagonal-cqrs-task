package com.hexagonal.task.infra.repository

import com.hexagonal.task.domain.model.task.Task
import com.hexagonal.task.domain.model.task.TaskRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TaskRepositoryImpl : TaskRepository {

    override fun insert(task: Task) {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Optional<Task> {
        TODO("Not yet implemented")
    }

    override fun updateAgent(task: Task) {
        TODO("Not yet implemented")
    }

    override fun initializeTask(task: Task) {
        TODO("Not yet implemented")
    }

}