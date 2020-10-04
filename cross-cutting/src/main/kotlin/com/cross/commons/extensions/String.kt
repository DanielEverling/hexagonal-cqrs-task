package com.cross.commons.extensions

import com.cross.domain.Notification
import java.util.Optional
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.swing.text.MaskFormatter


val String.Companion.Empty: String
    get() = ""

fun String.hasPattern(pattern: String, message: String, field: String? = null) : Optional<Notification> {
    val pattern: Pattern = Pattern.compile(pattern)
    val matcher: Matcher = pattern.matcher(this)

    return when(matcher.matches()) {
        true -> Optional.empty()
        false -> Optional.of(Notification(notification = message, field = field))
    }
}

fun String.validateSizeLessThan(length: Int, message: String, field: String? = null) : Optional<Notification> {
    return when {
        this.length > length -> Optional.of(Notification(notification = message, field = field))
        else -> Optional.empty()
    }
}

fun String.isBlank(message: String, field: String? = null) : Optional<Notification> {
   return when {
       this.isBlank() -> Optional.of(Notification(notification = message, field = field))
       else -> Optional.empty()
    }
}

fun String.formattingWithMask(mask: String): String {
    val mf = MaskFormatter(mask)
    mf.valueContainsLiteralCharacters = false;
    return mf.valueToString(this)
}
