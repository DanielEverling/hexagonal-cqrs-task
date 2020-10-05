package com.hexagonal.agent.application.service.agent

import com.cross.commons.exception.BusinessException
import com.cross.commons.exception.EntityNotFoundException
import com.cross.domain.ResultEntity
import com.cross.events.commons.EventPublisher
import com.hexagonal.agent.application.dto.agent.AgentDTO
import com.hexagonal.agent.domain.agent.Agent
import com.hexagonal.agent.domain.agent.AgentRepository
import com.hexagonal.agent.domain.agent.CreateAgentSpecification
import org.springframework.stereotype.Service
import java.util.*

@Service
class AgentService(private val agentRepository: AgentRepository,
                   private val createAgentSpecification: CreateAgentSpecification,
                   private val eventPublisher: EventPublisher) {

    fun insert(agentDTO: AgentDTO) {
        val agentResult = Agent.build(createAgentSpecification) {
            id = agentDTO.id
            name = agentDTO.name
            cpf = agentDTO.cpf
            email = agentDTO.email
            address = agentDTO.address
            birthDay = agentDTO.birthDay
        }

        when(agentResult) {
            is ResultEntity.Success -> agentRepository.insert(agent = agentResult.entity)
            is ResultEntity.Failure -> throw BusinessException(agentResult.notifications)
        }
    }

    fun update(agentDTO: AgentDTO) {
        val agentResult = Agent.build {
           agentDTO.also {
               this.id = it.id
               this.name = it.name
               this.cpf = it.cpf
               this.email = it.email
               this.address = it.address
               this.birthDay = it.birthDay
           }
        }

        when(agentResult) {
            is ResultEntity.Success -> {
                val agent = agentResult.entity
                agent.inactive()
                agentRepository.update(agent = agentResult.entity)
                if (agent.hasDomainEvents) {
                    agent.domainEvents.forEach {
                        eventPublisher.publisher(it)
                    }
                }
            }
            is ResultEntity.Failure -> throw BusinessException(agentResult.notifications)
        }
    }

    fun findById(id: UUID): AgentDTO {
        val existsAgent = agentRepository.findById(id);

        return when(existsAgent.isEmpty) {
            true -> throw EntityNotFoundException(id.toString())
            false -> {
                val agent = existsAgent.get()
                agent.let {
                    AgentDTO(
                        id = it.id,
                        name = it.name.value,
                        cpf = it.cpf.value,
                        email = it.email.value,
                        address = it.address,
                        birthDay = it.birthDay,
                        active = it.active
                    )
                }
            }
        }
    }
}