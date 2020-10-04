package com.hexagonal.agent.infra.agent

import com.hexagonal.agent.domain.agent.Agent
import com.hexagonal.agent.domain.agent.AgentRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AgentRepositoryImpl : AgentRepository {

    override fun insert(agent: Agent) {
        TODO("Not yet implemented")
    }

    override fun update(agent: Agent) {
        TODO("Not yet implemented")
    }

    override fun findById(id: UUID): Optional<Agent> {
        TODO("Not yet implemented")
    }

}