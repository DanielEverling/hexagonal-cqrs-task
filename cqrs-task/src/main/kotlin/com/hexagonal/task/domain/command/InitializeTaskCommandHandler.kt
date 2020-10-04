package com.hexagonal.task.domain.command

import com.cross.commands.BaseCommandHandler
import com.cross.events.commons.EntityNotFoundEvent
import com.cross.events.task.InitializeTaskEvent
import com.hexagonal.task.domain.model.task.TaskRepository
import org.springframework.stereotype.Service

@Service
class InitializeTaskCommandHandler(private val taskRepository: TaskRepository) : BaseCommandHandler<InitializeTaskCommand>() {

    override fun handler(command: InitializeTaskCommand) {
        val isThereTask = taskRepository.findById(command.taskId)

        if(isThereTask.isEmpty) {
            eventPublisher.publisher(EntityNotFoundEvent("Task with id ${command.taskId} not found"))
            return
        }

        val task = isThereTask.get()
        task.initializeActivity()
        taskRepository.initializeTask(task)
        eventPublisher.publisher(InitializeTaskEvent(id = task.id, data = task.activity.initializeData!!))
    }

}