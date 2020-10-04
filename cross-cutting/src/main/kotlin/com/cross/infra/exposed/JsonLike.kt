package com.cross.infra.exposed

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ComparisonOp
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.stringParam

class JsonLike(expr1: Expression<*>, expr2: Expression<*>) : ComparisonOp(expr1,expr2, "::text LIKE")

infix fun <T : Any?> Column<T>.containsInJsonB(pattern: Any): JsonLike {
    val filter = createObjectMapper().writeValueAsString(pattern)
    return JsonLike(this, stringParam("%$filter%"))
}
