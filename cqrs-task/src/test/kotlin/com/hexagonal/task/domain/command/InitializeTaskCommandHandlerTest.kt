package com.hexagonal.task.domain.command

import com.cross.events.commons.EntityNotFoundEvent
import com.cross.events.commons.EventPublisher
import com.cross.events.task.InitializeTaskEvent
import com.hexagonal.task.domain.helper.createTask
import com.hexagonal.task.domain.model.task.Task
import com.hexagonal.task.domain.model.task.TaskRepository
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class InitializeTaskCommandHandlerTest {

    private val taskRepository: TaskRepository = mock()

    private val eventPublisher: EventPublisher = mock()

    private lateinit var initializeTaskCommandHandler: InitializeTaskCommandHandler

    @BeforeEach
    fun `setUp`() {
        initializeTaskCommandHandler = InitializeTaskCommandHandler(taskRepository).also {
            it.eventPublisher = eventPublisher
        }
    }

    @Test
    fun `should process initialize task command with success`() {
        val taskId = UUID.randomUUID()
        val initializeTaskCommand = InitializeTaskCommand(taskId = taskId)
        whenever(taskRepository.findById(taskId)).thenReturn(Optional.of(createTask(taskId)))

        initializeTaskCommandHandler.handler(initializeTaskCommand)
        verify(taskRepository, times(1)).findById(taskId)
        verify(taskRepository, times(1)).initializeTask(any<Task>())
        verify(eventPublisher, times(1)).publisher(any<InitializeTaskEvent>())
        verify(eventPublisher, never()).publisher(any<EntityNotFoundEvent>())
    }

    @Test
    fun `should throw entity not fount event when task not found`() {
        val taskId = UUID.randomUUID()
        val initializeTaskCommand = InitializeTaskCommand(taskId = taskId)
        whenever(taskRepository.findById(taskId)).thenReturn(Optional.empty())

        initializeTaskCommandHandler.handler(initializeTaskCommand)
        verify(taskRepository, times(1)).findById(taskId)
        verify(eventPublisher, times(1)).publisher(any<EntityNotFoundEvent>())

        verify(taskRepository, never()).initializeTask(any<Task>())
        verify(eventPublisher, never()).publisher(any<InitializeTaskEvent>())
    }
}