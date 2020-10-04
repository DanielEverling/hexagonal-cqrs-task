package com.hexagonal.agent.domain.agent

import com.cross.domain.Notification
import com.cross.domain.ResultEntity
import com.cross.domain.vo.Address
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.time.LocalDate
import java.util.*

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain same`

internal class AgentTest() {

    @Test
    fun `should create an agent valid with success` () {
        val expectedId = UUID.randomUUID()
        val expectedFullName = "Elias Olegues Hexsel"
        val expectedCpf = "03373746949"
        val expectedAddress = Address(street = "Cristiano Fischer", city = "POA", state = "RS", cep = "90512-512", number = "465", complement = "NA", neighborhood = "Petropolis")
        val expectedEmail = "eoh@mail.com"
        val expectedBirthDay = LocalDate.now().minusYears(20)

        val resultAgent = Agent.build {
            id = expectedId
            name = expectedFullName
            cpf = expectedCpf
            email = expectedEmail
            address = expectedAddress
            birthDay = expectedBirthDay
        }

        when (resultAgent) {
            is ResultEntity.Failure -> {
                fail { "The creation of a agent must be with success -> ${resultAgent.notifications}" }
            }
            is ResultEntity.Success -> {
                val agent = resultAgent.entity
                agent.id `should be equal to` expectedId
                agent.name.value `should be equal to` expectedFullName
                agent.email.value `should be equal to` expectedEmail
                agent.address `should be equal to` expectedAddress
                agent.cpf.value `should be equal to` expectedCpf
                agent.birthDay `should be equal to` expectedBirthDay
                agent.active `should be equal to` true
            }
        }
    }

    @Test
    fun `should validate the creation of an agent with empty fields ` () {
        val resultAgent = Agent.build {
            name = ""
            cpf = ""
            email = ""
            address = Address(street = "", number = "", cep = "", city = "", state = "", neighborhood = "")
            birthDay = LocalDate.now()
        }

        when (resultAgent) {
            is ResultEntity.Success -> {
                fail { "The creation of a agent must be with fail" }
            }
            is ResultEntity.Failure -> {
                val expectedNotifications = listOf(
                        Notification(notification = "CPF é obrigatório."),
                        Notification(notification = "Nome é obrigatório."),
                        Notification(notification = "Rua é obrigatório."),
                        Notification(notification = "Bairro é obrigatório."),
                        Notification(notification = "Informe um CEP válido."),
                        Notification(notification = "Cidade é obrigatório."),
                        Notification(notification = "Estado é obrigatório."),
                        Notification(notification = "Informe um e-mail válido."),
                )

                expectedNotifications.size `should be equal to` resultAgent.notifications.size
                expectedNotifications `should contain same` resultAgent.notifications
            }
        }
    }
}