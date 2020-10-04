package com.cross.domain.vo

import com.cross.domain.Notification
import com.cross.domain.ValidatorsAware
import com.cross.commons.extensions.hasPattern
import com.cross.commons.extensions.isBlank
import com.cross.infra.MessageBundle.Companion.message
import java.util.Optional

enum class ContactType {
    EMAIL {
        private val pattern = "^([_A-Za-z0-9-\\+]{0,50}+(\\.[_A-Za-z0-9-]{0,50}+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))$"
        override fun validate(value: String) =
                value.hasPattern(pattern = pattern, message = message("field.invalid", "e-mail"))

    },
    PHONE {
        override fun validate(value: String) =
                value.isBlank(message = message("field.required", "Telefone / celular"))
    };

    abstract fun validate(value: String): Optional<Notification>
}

open class Contact(open val value: String, val type: ContactType, val complement:String = ""): ValidatorsAware {

    override fun validators() = listOf(
            type.validate(value)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (value != other.value) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }


}

data class Email(override val value: String) : Contact(value, type = ContactType.EMAIL)

data class Phone(override val value: String) : Contact(value, type = ContactType.PHONE)



