package com.hexagonal.agent.domain.agent

import java.util.*

interface AgentRepository {

    fun insert(agent: Agent)

    fun update(agent: Agent)

    fun findById(id: UUID): Optional<Agent>

}