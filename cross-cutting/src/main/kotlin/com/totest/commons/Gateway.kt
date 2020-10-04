package com.totest.commons

import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

const val MEDIA_TYPE = "application/json"

object Gateway {

    fun post(path: String, body: String, accessToken:String) : Pair<Int, String> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .post(body.toRequestBody(MEDIA_TYPE.toMediaType()))
                        .headers(addHeaders(accessToken))
                        .build())
                .execute()

        return when(response.code) {
            201 -> {
                val location = response.header("location")!!
                Pair(response.code, location)
            }
            else -> {
                Pair(response.code, response.body!!.string())
            }
        }
    }

    fun patch(path: String, body: String, accessToken: String) : Pair<Int, String> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .patch(body.toRequestBody(MEDIA_TYPE.toMediaType()))
                        .headers(addHeaders(accessToken))
                        .build())
                .execute()
        return Pair(response.code, response.body!!.string())
    }

    fun put(path: String, body: String, accessToken: String): Pair<Int, String> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .put(body.toRequestBody(MEDIA_TYPE.toMediaType()))
                        .headers(addHeaders(accessToken))
                        .build())
                .execute()

        return Pair(response.code, response.body!!.string())
    }

    fun delete(path: String, body: String, accessToken: String) : Pair<Int, String> {
        val response: Response = OkHttpClient()
                .newCall(Request.Builder()
                        .url("$BASE_URL/$path")
                        .delete(body.toRequestBody(MEDIA_TYPE.toMediaType()))
                        .headers(addHeaders(accessToken))
                        .build())
                .execute()
        return Pair(response.code, response.body!!.string())
    }

    fun get(path: String, accessToken: String): Pair<Int, String> {
        val response= OkHttpClient()
                .newCall(Request
                        .Builder()
                        .url("$BASE_URL/$path")
                        .headers(addHeaders(accessToken))
                        .build())
                .execute()

        return Pair(response.code, response.body!!.string())
    }

    private fun addHeaders(accessToken: String): Headers {
        val headers = mapOf(
                "Accept" to MEDIA_TYPE,
                "Content-Type" to MEDIA_TYPE,
                "Authorization" to "Bearer $accessToken"
        )
        return headers.toHeaders()
    }

}
