package com.cross.domain.vo

import com.cross.domain.Notification
import com.cross.domain.ValidatorsAware
import com.cross.commons.extensions.hasPattern
import com.cross.commons.extensions.isBlank
import com.cross.commons.extensions.validateSizeLessThan
import com.cross.infra.FIELD_INVALID
import com.cross.infra.FIELD_MAXLENGTH
import com.cross.infra.FIELD_REQUIRED
import com.cross.infra.MessageBundle
import java.util.Optional

data class Address(val street: String,
                   val number: String,
                   val neighborhood: String,
                   val cep: String,
                   val city: String,
                   val state: String,
                   val complement: String = "") : ValidatorsAware {

    override fun validators(): List<Optional<Notification>> = listOf(
            street.isBlank(message = MessageBundle.message(FIELD_REQUIRED, "Rua")),
            street.validateSizeLessThan(100, MessageBundle.message(FIELD_MAXLENGTH, "Rua", "100")),
            number.validateSizeLessThan(6, MessageBundle.message(FIELD_MAXLENGTH, "Numero", "6")),
            neighborhood.isBlank(MessageBundle.message(FIELD_REQUIRED, "Bairro")),
            neighborhood.validateSizeLessThan(length = 50, message = MessageBundle.message(FIELD_MAXLENGTH, "Bairro", "50")),
            cep.hasPattern(pattern = "\\d{5}-\\d{3}", message = MessageBundle.message(FIELD_INVALID, "CEP")),
            city.isBlank(message = MessageBundle.message(FIELD_REQUIRED, "Cidade")),
            city.validateSizeLessThan(length = 50, message = MessageBundle.message(FIELD_MAXLENGTH, "Cidade", "50")),
            state.isBlank(message = MessageBundle.message(FIELD_REQUIRED, "Estado")),
            state.validateSizeLessThan(length = 2, message = MessageBundle.message(FIELD_MAXLENGTH, "Estado", "2")),
            complement.validateSizeLessThan(100, MessageBundle.message(FIELD_MAXLENGTH, "Complemento", "100"))
    )
}