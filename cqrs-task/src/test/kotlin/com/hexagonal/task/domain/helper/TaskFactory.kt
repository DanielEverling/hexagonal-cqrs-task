package com.hexagonal.task.domain.helper

import com.cross.domain.vo.Address
import com.cross.domain.vo.FullName
import com.cross.domain.vo.Period
import com.hexagonal.task.domain.model.task.Activity
import com.hexagonal.task.domain.model.task.Agent
import com.hexagonal.task.domain.model.task.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun createTask(id: UUID = UUID.randomUUID()) = Task.build {
    this.id = id
    this.activity = Activity(description = "Entregar a torta de chocolate", date = LocalDate.now().atTime(15, 30))
    this.agent = Agent(id = UUID.randomUUID(), name = FullName("Joao da Silva"))
    this.local = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
    this.period = Period(start = LocalDateTime.now(), end = LocalDateTime.now().plusHours(2))
    this.items = mutableListOf("Item 1", "Item 2")
}.entity()