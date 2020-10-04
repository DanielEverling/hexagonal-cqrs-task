package com.hexagonal.task.domain.command

import com.cross.commands.BaseCommandHandler
import com.cross.domain.ResultEntity.*
import com.cross.events.commons.DomainInvalidEvent
import com.cross.events.task.CreatedTaskEvent
import com.hexagonal.task.domain.model.task.Task
import com.hexagonal.task.domain.model.task.TaskRepository
import org.springframework.stereotype.Service

@Service
class CreateTaskCommandHandler(private val taskRepository: TaskRepository): BaseCommandHandler<CreateTaskCommand>() {

    override fun handler(command: CreateTaskCommand) {
        val taskResult = Task.build {
            id = command.id
            activity = command.activity
            agent = command.agent
            local = command.local
            period = command.period
            items = command.items
        }

        when(taskResult) {
            is Success -> {
                val newTask = taskResult.entity
                taskRepository.insert(task = newTask)
                val createdTaskEvent = CreatedTaskEvent(id = newTask.id,
                        activity = newTask.activity.description,
                        agent = newTask.agent.name.value,
                        local = newTask.local,
                        data = newTask.createdAt
                )
                eventPublisher.publisher(createdTaskEvent)
            }
            is Failure -> eventPublisher.publisher(DomainInvalidEvent(taskResult.notifications))
        }
    }
}