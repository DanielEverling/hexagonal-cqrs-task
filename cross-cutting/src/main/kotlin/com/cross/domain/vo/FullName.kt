package com.cross.domain.vo

import com.cross.domain.ValidatorsAware
import com.cross.commons.extensions.isBlank
import com.cross.commons.extensions.validateSizeLessThan
import com.cross.infra.FIELD_MAXLENGTH
import com.cross.infra.FIELD_REQUIRED
import com.cross.infra.MessageBundle

data class FullName(val value: String): ValidatorsAware {

    override fun validators() = listOf(
            value.isBlank(message = MessageBundle.message(FIELD_REQUIRED, "Nome")),
            value.validateSizeLessThan(100, MessageBundle.message(FIELD_MAXLENGTH, "Nome", "100"))
    )
}