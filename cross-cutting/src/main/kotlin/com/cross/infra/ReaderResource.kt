package com.cross.infra

import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ReaderResource {

    fun loadAsStream(file: String): InputStream = this::class.java.classLoader.getResourceAsStream(file)

}