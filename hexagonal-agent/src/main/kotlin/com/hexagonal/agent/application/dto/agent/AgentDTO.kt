package com.hexagonal.agent.application.dto.agent

import com.cross.domain.vo.Address
import java.time.LocalDate
import java.util.*

data class AgentDTO(var id: UUID = UUID.randomUUID(),
                    val name: String,
                    val cpf: String,
                    val email: String,
                    val address: Address,
                    val birthDay: LocalDate,
                    var active: Boolean)