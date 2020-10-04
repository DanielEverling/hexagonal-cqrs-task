package com.cross.infra

import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle

const val REGISTER_ALREADY_EXISTS = "register.already.exists"
const val FIELD_REQUIRED = "field.required"
const val FIELD_MAXLENGTH = "field.maxlength"
const val FIELD_INVALID = "field.invalid"
const val BIRTHDAY_INVALID = "field.invalid"

class MessageBundle {

    companion object {

        private fun readMessageBundle() = ResourceBundle.getBundle("messages", Locale.getDefault())

        fun message(key: String): String {
            return try {
                readMessageBundle().getString(key)
            } catch (e: MissingResourceException) {
                "??$key??"
            }
        }

        fun message(key: String, vararg replaceValue: String): String {
            val originalMessage = message(key)
            return replaceOriginalMessageByValueParameters(originalMessage, replaceValue)
        }

        private fun replaceOriginalMessageByValueParameters(originalMessage: String, replaceValue: Array<out String>): String {
            var newMessage = originalMessage
            replaceValue.forEach { newValue ->
                newMessage = newMessage.replaceFirst("{value}", newValue)
            }
            return newMessage
        }

    }
}
