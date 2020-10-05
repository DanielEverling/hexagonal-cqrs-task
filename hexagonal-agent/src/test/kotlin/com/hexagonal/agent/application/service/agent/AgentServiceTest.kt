package com.hexagonal.agent.application.service.agent

import com.cross.commons.exception.BusinessException
import com.cross.commons.exception.EntityNotFoundException
import com.cross.domain.Notification
import com.cross.domain.vo.Address
import com.cross.events.commons.EventPublisher
import com.hexagonal.agent.application.dto.agent.AgentDTO
import com.hexagonal.agent.domain.agent.Agent
import com.hexagonal.agent.domain.agent.AgentRepository
import com.hexagonal.agent.helper.createAgent
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.*

internal class AgentServiceTest {

    private val agentRepository: AgentRepository = mock()

    private val eventPublisher: EventPublisher = mock()

    private lateinit var agentService: AgentService

    @BeforeEach
    fun `setUp`() {
        agentService = AgentService(agentRepository, eventPublisher)
    }

    @Test
    fun `should insert a agent`() {
        val address = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
        val newAgent = AgentDTO(name = "Renato Portaluppi", cpf = "49192013031", email = "renatoPapaInter@gremio.net", address = address, birthDay = LocalDate.now().minusYears(44), active = true)

        agentService.insert(agentDTO = newAgent)
        verify(agentRepository, times(1)).insert(any<Agent>())
    }

    @Test
    fun `should validate inserting when agent is not valid`() {
        val address = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
        val newAgent = AgentDTO(name = "Renato Portaluppi", cpf = "123131", email = "renatoPapaInter@gremio.net", address = address, birthDay = LocalDate.now().plusDays(7), active = true)

        val expectedNotification = listOf(
                Notification("Informe um Data de Nascimento válido."),
                Notification("Informe um CPF válido.")
        )

        assertThrows<BusinessException> { agentService.insert(agentDTO = newAgent) }.apply {
            expectedNotification.forEach {
                this.message!! `should contain` it.notification
            }
        }
    }

    @Test
    fun `should update an agent`() {
        val address = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
        val existsAgent = AgentDTO(id= UUID.randomUUID(), name = "Renato Portaluppi", cpf = "49192013031", email = "renatoPapaInter@gremio.net", address = address, birthDay = LocalDate.now().minusYears(44), active = true)

        agentService.update(agentDTO = existsAgent)
        verify(agentRepository, times(1)).update(any<Agent>())
    }

    @Test
    fun `should validate updating when agent is not valid`() {
        val address = Address(street = "Cristiano Fischer", city = "", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
        val existsAgent = AgentDTO(id= UUID.randomUUID(), name = "Renato Portaluppi", cpf = "", email = "", address = address, birthDay = LocalDate.now().plusDays(1), active = true)

        val expectedNotification = listOf(
                Notification("Informe um Data de Nascimento válido."),
                Notification("CPF é obrigatório."),
                Notification("Informe um e-mail válido."),
                Notification("Cidade é obrigatório.")
        )

        assertThrows<BusinessException> { agentService.update(agentDTO = existsAgent) }.apply {
            expectedNotification.forEach {
                this.message!! `should contain` it.notification
            }
        }
    }

    @Test
    fun `should find an agent by id`() {
        val id = UUID.randomUUID()
        val agent = createAgent(id)
        whenever(agentRepository.findById(id)).thenReturn(Optional.of(agent))

        val agentFound = agentService.findById(id)

        agentFound.active `should be equal to` agent.active
        agentFound.name `should be equal to` agent.name.value
        agentFound.address `should be equal to` agent.address
        agentFound.cpf `should be equal to` agent.cpf.value
        agentFound.birthDay `should be equal to` agent.birthDay
        agentFound.id `should be equal to` agent.id
    }

    @Test
    fun `should not find an agent by id`() {
        val id = UUID.randomUUID()
        whenever(agentRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows<EntityNotFoundException> { agentService.findById(id) }.apply {
            this.message!! `should contain` "Entity not found by value $id"
        }
    }

}