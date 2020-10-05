package com.hexagonal.agent.domain.agent

import com.cross.domain.Notification
import com.cross.domain.Specification
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateAgentSpecification (val repository: AgentRepository): Specification<Agent>() {

    override fun isSatisfiedBy(entity: Agent): Optional<Notification> {
        val isThereAgent = repository.findByCPF(entity.cpf)

        return when(isThereAgent.isEmpty) {
            true -> Optional.empty()
            false -> Optional.of(Notification("Agente ja cadastrado"))
        }
    }
}