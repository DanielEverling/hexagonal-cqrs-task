package com.cross.domain.vo

import com.cross.domain.toListNotification
import org.amshove.kluent.`should be equal to`
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test

internal class FullNameTest {

    @Test
    fun `should create a full name with success`() {
        val fullName = FullName("Renato Portallupi")
        fullName.validators().toListNotification().isEmpty() `should be equal to`  true
    }

    @Test
    fun `should validate a full name with empty name`() {
        val fullName = FullName("")
        fullName.validators().toListNotification().isEmpty() `should be equal to`  false
        fullName.validators().toListNotification()[0].notification `should be equal to` "Nome é obrigatório."
    }

    @Test
    fun `should validate a full name with invalid name`() {
        val fullName = FullName(RandomStringUtils.randomAlphabetic(200))
        fullName.validators().toListNotification().isEmpty() `should be equal to`  false
        fullName.validators().toListNotification()[0].notification `should be equal to` "Nome deve ter no máximo 100 caracteres."
    }
}