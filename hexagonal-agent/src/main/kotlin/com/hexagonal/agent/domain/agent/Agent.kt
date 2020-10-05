package com.hexagonal.agent.domain.agent

import com.cross.commons.extensions.isBlank
import com.cross.commons.extensions.isFuture
import com.cross.domain.*
import com.cross.domain.vo.Address
import com.cross.domain.vo.Cpf
import com.cross.domain.vo.Email
import com.cross.domain.vo.FullName
import java.time.LocalDate
import com.cross.infra.MessageBundle.Companion.message
import com.cross.infra.FIELD_REQUIRED
import com.cross.infra.BIRTHDAY_INVALID
import java.time.LocalDateTime
import java.util.*

data class Agent private constructor(val id: UUID = UUID.randomUUID(),
                                     val name: FullName,
                                     val cpf: Cpf,
                                     val email: Email,
                                     val address: Address,
                                     val birthDay: LocalDate,
                                     var active: Boolean = true,
                                     private val specification: Specification<Agent>): Entity(), AggregateRoot {

    init {
        validate()
    }

    fun inactive() {
        this.active = false
        this.addDomainEvent(AgentInactivatedEvent(id = id, name = name.value, date = LocalDateTime.now()))
    }

    override fun validators() = listOf(
            cpf.value.isBlank(message(FIELD_REQUIRED, "CPF")),
            birthDay.isFuture(message(BIRTHDAY_INVALID, "Data de Nascimento")),
            specification.isSatisfiedBy(this)
    ) + name.validators() + email.validators() + cpf.validators() + address.validators()


    companion object {
        inline fun build(specification: Specification<Agent> = Specification(), block: Agent.Builder.() -> Unit) = Builder(specification).apply(block).build()
    }

    class Builder (private val specification: Specification<Agent>) {

        var id: UUID = UUID.randomUUID()
        lateinit var name: String
        lateinit var email: String
        lateinit var cpf: String
        lateinit var address: Address
        lateinit var birthDay: LocalDate

        fun build() : ResultEntity<List<Notification>, Agent> {
            val newAgent = Agent(id = id,
                    name = FullName(name),
                    email = Email(email),
                    cpf = Cpf(cpf),
                    address = address,
                    birthDay = birthDay,
                    specification = specification)

            return  when(newAgent.isValid()) {
                true -> ResultEntity.Success<Agent>(newAgent)
                else -> ResultEntity.Failure(newAgent.notifications)
            }
        }
    }
}