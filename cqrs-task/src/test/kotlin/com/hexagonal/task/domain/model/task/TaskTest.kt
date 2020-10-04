package com.hexagonal.task.domain.model.task

import com.cross.domain.ResultEntity
import com.cross.domain.vo.Address
import com.cross.domain.vo.FullName
import com.cross.domain.vo.Period
import com.hexagonal.task.domain.helper.createTask
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

internal class TaskTest {

    @Test
    fun `should create a task with success`() {
        val expectedId = UUID.randomUUID()
        val expectedActivity = Activity(description = "Entregar a torta de chocolate", date = LocalDate.now().atTime(15, 30))
        val expectedAgent = Agent(id = UUID.randomUUID(), name = FullName("Joao da Silva"))
        val expectedLocal = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
        val expectedPeriod = Period(start = LocalDateTime.now(), end = LocalDateTime.now().plusHours(2))
        val expectedItems= mutableListOf("Item 1", "Item 2")

        val newTaskResult = Task.build {
            id = expectedId
            activity = expectedActivity
            agent = expectedAgent
            local = expectedLocal
            period = expectedPeriod
            items = expectedItems
        }

        when(newTaskResult) {
            is ResultEntity.Failure -> {
                fail("Task should be created with success")
            }
            is ResultEntity.Success -> {
                val newTask = newTaskResult.entity
                newTask.id `should be equal to` expectedId
                newTask.activity `should be equal to` expectedActivity
                newTask.agent `should be equal to` expectedAgent
                newTask.local `should be equal to` expectedLocal
                newTask.period `should be equal to` expectedPeriod
                newTask.items `should be equal to` expectedItems
                newTask.createdAt `should not be` null
            }
        }
    }

    @Test
    fun `should change agent of task`() {
        val expectedNewAgent = Agent(id = UUID.randomUUID(), name = FullName("Renato Portallupi"))
        val oldTask = createTask()
        oldTask.agent.name `should be equal to` FullName("Joao da Silva")
        oldTask.changeAgent(newAgent = expectedNewAgent)
        oldTask.agent `should be equal to` expectedNewAgent
    }

}