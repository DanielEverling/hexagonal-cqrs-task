package com.hexagonal.task.domain.command

import com.cross.commands.BaseCommandHandler
import com.cross.events.commons.EntityNotFoundEvent
import com.cross.events.task.ChangedAgentTaskEvent
import com.hexagonal.task.domain.model.task.TaskRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChangeAgentCommandHandler (private val taskRepository: TaskRepository): BaseCommandHandler<ChangeAgentCommand>() {

    override fun handler(command: ChangeAgentCommand) {
        val isThereTask = taskRepository.findById(command.taskId)

        if (isThereTask.isEmpty) {
            eventPublisher.publisher(EntityNotFoundEvent("Task with id ${command.taskId} not found"))
            return
        }

        val task = isThereTask.get()
        task.changeAgent(command.newAgent)
        taskRepository.updateAgent(task)
        eventPublisher.publisher(ChangedAgentTaskEvent(id = task.id, agent = task.agent.name.value, data = LocalDateTime.now()))
    }

}