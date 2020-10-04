package com.cross.domain.vo

import com.cross.domain.Notification
import com.cross.domain.toListNotification
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain same`
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test

internal class AddressTest {

    @Test
    fun `should create address with success` () {
        val address = Address(street = "Beco Caiçaras", number = "535", cep = "20211-400", city = "Rio de Janeiro", state = "RJ", neighborhood = "Catumbi")
        address.validators().toListNotification().size `should be equal to` 0
    }

    @Test
    fun `should validate address with empty fields` () {
        val address = Address(street = "", number = "", cep = "", city = "", state = "", neighborhood = "")
        val expectedNotification = listOf(
                Notification(notification = "Rua é obrigatório."),
                Notification(notification = "Bairro é obrigatório."),
                Notification(notification = "Informe um CEP válido."),
                Notification(notification = "Cidade é obrigatório."),
                Notification(notification = "Estado é obrigatório.")
        )

        address.validators().toListNotification() `should contain same`  expectedNotification
    }

    @Test
    fun `should validate address with invalid fields` () {
        val invalidStringByMaxLength = RandomStringUtils.randomAlphabetic(200)
        val address = Address( street = invalidStringByMaxLength,
                number = invalidStringByMaxLength,
                cep = invalidStringByMaxLength,
                city = invalidStringByMaxLength,
                state = invalidStringByMaxLength,
                neighborhood = invalidStringByMaxLength)

        val expectedNotification = listOf(
                Notification(notification = "Rua deve ter no máximo 100 caracteres."),
                Notification(notification = "Numero deve ter no máximo 6 caracteres."),
                Notification(notification = "Bairro deve ter no máximo 50 caracteres."),
                Notification(notification = "Informe um CEP válido."),
                Notification(notification = "Cidade deve ter no máximo 50 caracteres."),
                Notification(notification = "Estado deve ter no máximo 2 caracteres.")
        )

        address.validators().toListNotification() `should contain same`  expectedNotification
    }
}