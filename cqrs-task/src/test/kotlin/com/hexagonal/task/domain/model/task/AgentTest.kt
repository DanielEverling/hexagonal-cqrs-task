package com.hexagonal.task.domain.model.task

import com.cross.domain.Notification
import com.cross.domain.toListNotification
import com.cross.domain.vo.FullName
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.Test
import java.util.*

internal class AgentTest {

    @Test
    fun `should create an agent`() {
        val newAgent = Agent(id = UUID.randomUUID(), name = FullName("Jose da Silva"))
        newAgent.name.value `should be equal to` "Jose da Silva"
    }

    @Test
    fun `should validate the creation of not valid agent`() {
        val newAgent = Agent(id = UUID.randomUUID(), name = FullName(""))
        val expectedNotifications = listOf(Notification("Nome é obrigatório."))
        newAgent.validators().toListNotification() `should contain same` expectedNotifications
    }

}