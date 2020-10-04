package com.cross.domain.vo

import com.cross.domain.Notification
import com.cross.domain.ValidatorsAware
import com.cross.commons.extensions.formattingWithMask
import com.cross.infra.MessageBundle
import org.apache.commons.lang3.StringUtils
import java.util.Optional

enum class DocumentType {
    CPF {
        private val baseForCalculatingCpf: IntArray = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)
        override fun validate(value: String): Optional<Notification> {
            return when (isValid(value, baseForCalculatingCpf, 11)) {
                true -> Optional.empty()
                false -> Optional.of(Notification(notification = MessageBundle.message("field.invalid", "CPF")))
            }
        }

        override fun applyFormatting(value: String): String = value.formattingWithMask("###.###.###-##")

    },

    CNPJ {
        private val baseForCalculatingCnpj: IntArray = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
        override fun validate(value: String): Optional<Notification> {
            return when (isValid(value, baseForCalculatingCnpj, 14)) {
                true -> Optional.empty()
                false -> Optional.of(Notification(notification = MessageBundle.message("field.invalid", "CNPJ")))
            }
        }

        override fun applyFormatting(value: String): String = value.formattingWithMask("##.###.###/####-##")
    };

    abstract fun applyFormatting(value: String): String

    abstract fun validate(value: String): Optional<Notification>

    fun isValid(value: String, baseForCalculating: IntArray, expectedLength: Int) : Boolean {
        if (StringUtils.isBlank(value)) {
            return true
        }

        if (value.length != expectedLength || validateCharSequence(value, expectedLength)) {
            return false
        }
        val maxSubstring = expectedLength - 2
        val firstDigit: Int = calculateCheckerType(value.substring(0, maxSubstring), baseForCalculating)
        val secondDigit: Int = calculateCheckerType(value.substring(0, maxSubstring) + firstDigit, baseForCalculating)
        return value == value.substring(0, maxSubstring) + firstDigit.toString() + secondDigit.toString()
    }

    private fun calculateCheckerType(str: String, baseForCalculating: IntArray): Int {
        var sum = 0
        var digit: Int
        for (index in str.length - 1 downTo 0) {
            digit = str.substring(index, index + 1).toInt()
            sum += digit * baseForCalculating[baseForCalculating.size - str.length + index]
        }
        sum = 11 - sum % 11
        return if (sum > 9) 0 else sum
    }

    private fun validateCharSequence(document: String, expectedLength: Int): Boolean {
        var documentSequence: String
        for (i in 0..expectedLength) {
            documentSequence = ""
            for (j in 0..expectedLength) {
                documentSequence += i.toString()
            }
            if (document == documentSequence) {
                return true
            }
        }
        return false
    }

}

open class Document(open val value: String, val type: DocumentType): ValidatorsAware  {

    override fun validators() = listOf( type.validate(value) )

    fun valueFormatting() = type.applyFormatting(value)

}

data class Cpf(override val value: String) : Document(value, type = DocumentType.CPF)

data class Cnpj(override val value: String) : Document(value, type = DocumentType.CNPJ)