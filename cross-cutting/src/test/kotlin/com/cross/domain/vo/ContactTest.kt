package com.cross.domain.vo

import com.cross.domain.Notification
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.Optional

internal class ContactTest {

    @Test
    fun `should create a valid email`() {
        val email = Contact("an.valid.email@emailOk.com", ContactType.EMAIL)
        email.validators()  `should be equal to` listOf(Optional.empty())
    }

    @Test
    fun `should validate a invalid email`() {
        val email = Contact("invalidEmail", ContactType.EMAIL)
        email.validators()[0].get().notification  `should be equal to` "Informe um e-mail válido."
    }

    @Test
    fun `should create a phone with success`() {
        val phone = Contact("51991919191", ContactType.PHONE)
        phone.validators() `should be equal to` listOf(Optional.empty())
    }

    @Test
    fun `should validate the creation of a empty phone`() {
        val phone = Contact("", ContactType.PHONE)
        phone.validators() `should be equal to` listOf(Optional.of(Notification(notification = "Telefone / celular é obrigatório.")))
    }
}