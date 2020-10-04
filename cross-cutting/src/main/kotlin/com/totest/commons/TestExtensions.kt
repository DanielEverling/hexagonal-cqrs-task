package com.totest.commons

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Scanner


val BASE_URL = "http://" + getEnv("APP_HOST", "localhost") + ":8080"
val HEALTH = "${BASE_URL}/health"

const val UUID_REGEX = "([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}){1}"
const val HTTP_OK : Int = 200
const val HTTP_CREATED : Int = 201
const val HTTP_NO_CONTENT : Int = 204
const val HTTP_BAD_REQUEST : Int = 400
const val HTTP_CONFLICT : Int = 409

fun getEnv(name: String, defaultValue: String) = System.getenv(name) ?: defaultValue

private val MAPPER = ObjectMapper()
        .setVisibility(jacksonObjectMapper().serializationConfig.defaultVisibilityChecker
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE))
        .registerModule(KotlinModule())
        .registerModule(JSR310Module())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

fun <T> fromJson(json: String, clazz: Class<T>): T = MAPPER.readValue(json, clazz)
fun <T> fromJson(body: Any, clazz: Class<T>): T {
    val json = toJson(body)
    return fromJson(json, clazz)
}
fun toJson(body: Any): String = MAPPER.writeValueAsString(body)

fun waitForResource(url: String) {
    var attempts = 0
    repeat(1000) {
        attempts++

        @Suppress("TooGenericExceptionThrown")
        if (attempts == 30) {
            throw RuntimeException("Service not found! -> $url")
        }

        val response = OkHttpClient()
                .newCall(Request
                        .Builder()
                        .url(url)
                        .build())
                .execute()

        if (200 == response.code) {
            return
        }
    }
}

open class ReadFile {
    companion object {
        fun readAndReplace(folder: String, name: String, stringToReplace : Map<String, String>) : String {
            var file = readFileAsString("/__files$folder/$name.json")
            stringToReplace.forEach{ (k, v) ->
                file = file.replace(oldValue = k, newValue = v)
            }
            return file
        }

        fun read(folder: String, fileName: String) =
            readFileAsString("/file$folder/$fileName.json")

        @Suppress("TooGenericExceptionCaught", "TooGenericExceptionThrown")
        fun readFileAsString(path: String): String {
            try {
                val inputStream = this::class.java.getResourceAsStream(path)

                val scanner = Scanner(inputStream, "UTF-8")
                val contentFile = scanner.useDelimiter("\\Z").next()
                scanner.close()

                return contentFile
            } catch (ex : Exception) {
                throw RuntimeException("File not found! -> $path")
            }
        }
    }
}
