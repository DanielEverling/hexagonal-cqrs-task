package com.cross.infra

import java.net.URI
import java.util.UUID

enum class Resources(val url: String) {
    ROOT("/")
}

class BaseLocation {

    companion object {
        fun location(resource: Resources, identifier: String) = URI("${resource.url}/${identifier}")
        fun location(resource: Resources, identifier: UUID) = location(resource, identifier.toString())
    }
}
