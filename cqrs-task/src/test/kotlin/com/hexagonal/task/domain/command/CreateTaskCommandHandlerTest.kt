package com.hexagonal.task.domain.command

import com.cross.domain.vo.Address
import com.cross.domain.vo.FullName
import com.cross.domain.vo.Period
import com.cross.events.commons.DomainInvalidEvent
import com.cross.events.commons.EventPublisher
import com.cross.events.task.CreatedTaskEvent
import com.hexagonal.task.domain.model.task.Activity
import com.hexagonal.task.domain.model.task.Agent
import com.hexagonal.task.domain.model.task.Task
import com.hexagonal.task.domain.model.task.TaskRepository
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

internal class CreateTaskCommandHandlerTest {

    private val taskRepository: TaskRepository = mock()

    private val eventPublisher: EventPublisher = mock()

    private lateinit var createTaskCommandHandler: CreateTaskCommandHandler

    @BeforeEach
    fun `setUp`() {
        createTaskCommandHandler = CreateTaskCommandHandler(taskRepository = taskRepository).also {
            it.eventPublisher = eventPublisher
        }
    }

    @Test
    fun `should process create task command with success`() {
        val createTaskCommand = CreateTaskCommand(
                activity = Activity(description = "Entregar a torta de chocolate", date = LocalDate.now().atTime(15, 30)),
                agent = Agent(id = UUID.randomUUID(), name = FullName("Joao da Silva")),
                local = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis"),
                period = Period(start = LocalDateTime.now(), end = LocalDateTime.now().plusHours(2)),
                items = mutableListOf("Item 1", "Item 2")
        )

        createTaskCommandHandler.handler(createTaskCommand)
        verify(taskRepository, times(1)).insert(any<Task>())
        verify(eventPublisher, times(1)).publisher(any<CreatedTaskEvent>())
        verify(eventPublisher, never()).publisher(any<DomainInvalidEvent>())
    }

    @Test
    fun `should throw domain invalid event when command is not valid`() {
        val invalidAgent = Agent(id = UUID.randomUUID(), name = FullName(""))

        val createTaskCommand = CreateTaskCommand(
                activity = Activity(description = "Entregar a torta de chocolate", date = LocalDate.now().atTime(15, 30)),
                agent = invalidAgent,
                local = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis"),
                period = Period(start = LocalDateTime.now(), end = LocalDateTime.now().plusHours(2)),
                items = mutableListOf("Item 1", "Item 2")
        )

        createTaskCommandHandler.handler(createTaskCommand)
        verify(eventPublisher, times(1)).publisher(any<DomainInvalidEvent>())
        verify(taskRepository, never()).insert(any<Task>())
        verify(eventPublisher, never()).publisher(any<CreatedTaskEvent>())

    }
}