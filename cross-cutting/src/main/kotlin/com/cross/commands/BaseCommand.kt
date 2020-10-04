package com.cross.commands

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import java.util.*

open abstract class BaseCommand {

    @JsonIgnore
    val id : UUID = UUID.randomUUID()

    @JsonIgnore
    val createdAt : LocalDateTime = LocalDateTime.now()

}