package com.hexagonal.task.domain.command

import com.cross.domain.vo.FullName
import com.cross.events.commons.EntityNotFoundEvent
import com.cross.events.commons.EventPublisher
import com.cross.events.task.ChangedAgentTaskEvent
import com.cross.events.task.InitializeTaskEvent
import com.hexagonal.task.domain.helper.createTask
import com.hexagonal.task.domain.model.task.Agent
import com.hexagonal.task.domain.model.task.Task
import com.hexagonal.task.domain.model.task.TaskRepository
import com.nhaarman.mockitokotlin2.*
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class ChangeAgentCommandHandlerTest {

    private val taskRepository: TaskRepository = mock()

    private val eventPublisher: EventPublisher = mock()

    private lateinit var changeAgentCommandHandler: ChangeAgentCommandHandler

    @BeforeEach
    fun `setUp`() {
        changeAgentCommandHandler = ChangeAgentCommandHandler(taskRepository).also {
            it.eventPublisher = eventPublisher
        }
    }

    @Test
    fun `should process change agent command with success`() {
        val newAgent = Agent(id = UUID.randomUUID(), name = FullName("Renato Portallupi"))
        val taskId = UUID.randomUUID()
        val changeAgentCommand = ChangeAgentCommand(taskId = taskId, newAgent = newAgent)

        whenever(taskRepository.findById(taskId)).thenReturn(Optional.of(createTask(taskId)))

        changeAgentCommandHandler.handler(changeAgentCommand)
        verify(taskRepository, times(1)).findById(taskId)
        verify(taskRepository, times(1)).updateAgent(any<Task>())
        verify(eventPublisher, times(1)).publisher(any<ChangedAgentTaskEvent>())
        verify(eventPublisher, never()).publisher(any<EntityNotFoundEvent>())
    }

    @Test
    fun `should throw entity not fount event when task not found`() {
        val newAgent = Agent(id = UUID.randomUUID(), name = FullName("Renato Portallupi"))
        val taskId = UUID.randomUUID()
        val changeAgentCommand = ChangeAgentCommand(taskId = taskId, newAgent = newAgent)

        whenever(taskRepository.findById(taskId)).thenReturn(Optional.empty())

        changeAgentCommandHandler.handler(changeAgentCommand)
        verify(taskRepository, times(1)).findById(taskId)
        verify(eventPublisher, times(1)).publisher(any<EntityNotFoundEvent>())

        verify(taskRepository, never()).initializeTask(any<Task>())
        verify(eventPublisher, never()).publisher(any<InitializeTaskEvent>())
    }

}