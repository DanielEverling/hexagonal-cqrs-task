package com.cross.commons.extensions

import com.cross.domain.Notification
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.Optional

class StringTest {

    private val expectedMessage = "expected message"

    @Test
    fun `should validate size less than X caracteres`() {
        val string = "some value"
        val hasOptional = string.validateSizeLessThan(20, expectedMessage)
        hasOptional `should be equal to` Optional.empty()
    }

    @Test
    fun `should validate when size bigger than X caracteres`() {
        val string = "some value"
        val hasOptional = string.validateSizeLessThan(5, expectedMessage)
        hasOptional `should be equal to` Optional.of(Notification(notification = expectedMessage))
    }

    @Test
    fun `should validate whether string is empty`() {
        val string:String = ""
        val hasOptional = string.isBlank(expectedMessage)
        hasOptional `should be equal to` Optional.of(Notification(notification = expectedMessage))
    }

    @Test
    fun `should validate whether string is not empty`() {
        val string:String = "some value"
        val hasOptional = string.isBlank(expectedMessage)
        hasOptional `should be equal to` Optional.empty()
    }


    @Test
    fun `should validate whether string doesn't have pattern`() {
        val string:String = "some value"
        val hasOptional = string.hasPattern("\\d{5}-\\d{3}", message = expectedMessage)
        hasOptional `should be equal to` Optional.of(Notification(notification = expectedMessage))
    }

    @Test
    fun `should validate whether string has pattern`() {
        val string:String = "90520-540"
        val hasOptional = string.hasPattern("\\d{5}-\\d{3}", message = "expectedMessage")
        hasOptional `should be equal to` Optional.empty()
    }

    @Test
    fun `should init string with empty value`() {
        String.Empty `should be equal to` ""
    }

    @Test
    fun `should formatting a string value`() {
        val cpf = "05167529018"
        cpf.formattingWithMask("###.###.###-##") `should be equal to` "051.675.290-18"
    }

}