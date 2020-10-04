package com.cross.infra.exposed

import com.cross.logs.logger
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import org.postgresql.util.PGobject

/**
 * Created by quangio.
 * @link https://gist.github.com/quangIO/a623b5caa53c703e252d858f7a806919
 */

fun createObjectMapper(): ObjectMapper {
    return ObjectMapper()
            .setVisibility(jacksonObjectMapper().serializationConfig.defaultVisibilityChecker
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE))
            .registerModule(KotlinModule())
            .registerModule(JSR310Module())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
}

fun <T : Any> Table.jsonb(name: String, klass: Class<T>): Column<T>
        = registerColumn(name, Json(klass, createObjectMapper(), false))

fun <T : Any> Table.jsonListb(name: String, klass: Class<T>): Column<List<T>>
        = registerColumn(name, Json(klass, createObjectMapper(), true))


private class Json<out T : Any>(private val klass: Class<T>, private val jsonMapper: ObjectMapper, private val isList: Boolean) : ColumnType() {

    val log = logger()

    override fun sqlType() = "jsonb"

    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
        val obj = PGobject()
        obj.type = "jsonb"
        obj.value = value as String
        stmt.set(index = index, value = obj)
    }

    override fun valueFromDB(value: Any): Any {
        if (value !is PGobject) {
            // We didn't receive a PGobject (the format of stuff actually coming from the DB).
            // In that case "value" should already be an object of type T.
            return value
        }

        // We received a PGobject, deserialize its String value.
        return if(isList) {
            val list = mutableListOf<Any>()
            val parse = jsonMapper.readValue(value.value, List::class.java)
            for (any in parse) {
                val json = jsonMapper.writeValueAsString(any)
                list.add(jsonMapper.readValue(json, klass))
            }
            list
        } else {
            jsonMapper.readValue(value.value, klass)
        }
    }

    override fun notNullValueToDB(value: Any): Any = jsonMapper.writeValueAsString(value)
    override fun nonNullValueToString(value: Any): String = "'${jsonMapper.writeValueAsString(value)}'"

}