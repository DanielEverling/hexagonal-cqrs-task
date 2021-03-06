package com.cross.logs

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger {
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java)
    }
    return LoggerFactory.getLogger(T::class.java)
}
