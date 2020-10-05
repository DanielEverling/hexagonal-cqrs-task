package com.hexagonal.agent.domain.agent

import com.cross.domain.vo.Cpf
import java.util.*

interface AgentRepository {

    fun insert(agent: Agent)

    fun update(agent: Agent)

    fun findById(id: UUID): Optional<Agent>

    fun findByCPF(cpf: Cpf): Optional<Agent>
}