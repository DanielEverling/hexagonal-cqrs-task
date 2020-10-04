package com.cross.commons.extensions

import com.cross.domain.Notification
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import java.util.Optional

internal class  OptionalTest {

    @Test
    fun `should return optional with value` () {
        val valueIsPresent = Optional.of("Some value")
        valueIsPresent.getOrEmpty() `should not be` Optional.empty<String>()
    }

    @Test
    fun `should return empty optional` () {
        val valueIsPresent = Optional.empty<String>()
        valueIsPresent.getOrEmpty() `should be equal to` Optional.empty<String>()
    }

    @Test
    fun `should concat two optional` () {
        val left = Optional.of(Notification("First Value"))
        val right = Optional.of(Notification("Second Value"))
        val result = left.and(right)
        result `should be equal to` Optional.of(Notification("First Value - Second Value"))
    }

}