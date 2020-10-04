package com.hexagonal.agent.helper

import com.cross.domain.vo.Address
import com.hexagonal.agent.domain.agent.Agent
import java.time.LocalDate
import java.util.*

fun createAgent(id: UUID = UUID.randomUUID()) = Agent.build {
    this.id = id
    this.name = "Jose da Silva"
    this.cpf = "44949048058"
    this.email = "js.silva@mail.com"
    this.address = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
    this.birthDay = LocalDate.now().minusYears(18)
}.entity()