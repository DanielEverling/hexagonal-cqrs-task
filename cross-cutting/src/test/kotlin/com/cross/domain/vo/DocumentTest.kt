package com.cross.domain.vo

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.Optional

internal class DocumentTest {

    @Test
    fun `should create a valid cpf`() {
        val cpf = Document("89621026091", DocumentType.CPF)
        cpf.validators()  `should be equal to` listOf(Optional.empty())
    }

    @Test
    fun `should validate a invalid cpf`() {
        val cpf = Document("111111111", DocumentType.CPF)
        cpf.validators()[0].get().notification  `should be equal to` "Informe um CPF válido."
    }

    @Test
    fun `should create a valid cnpj`() {
        val cnpj = Document("08411856000161", DocumentType.CNPJ)
        cnpj.validators()  `should be equal to` listOf(Optional.empty())
    }

    @Test
    fun `should validate a invalid cnpj`() {
        val cnpj = Document("12981716209183", DocumentType.CNPJ)
        cnpj.validators()[0].get().notification  `should be equal to` "Informe um CNPJ válido."
    }
}