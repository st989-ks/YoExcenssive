package ru.yo.excenssive_k.data

import android.content.Context
import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.date
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.yo.excenssive_k.models.AuthenticateData
import ru.yo.excenssive_k.models.BasicApi
import ru.yo.excenssive_k.models.HttpAppStatus
import ru.yo.excenssive_k.models.LocalAppSettings
import ru.yo.excenssive_k.models.UserData
import ru.yo.excenssive_k.util.dataStore

class APIClient(
    private val apiUrl: String = "http://85.92.108.5:8822/api/v1/",
    private val context: Context
) : Closeable {
    var token: String? = null


    private val client = HttpClient {
        token = getLocalData().token
        install(ContentNegotiation) {
            jackson {}
            json()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("HTTP Client", message)
                }
            }
            level = LogLevel.ALL
        }
        HttpResponseValidator {
            validateResponse {
                Log.i("APIClient", "HttpResponseValidator: ${it.status}")
//                when (it.status) {
//                    COMEBACK_LATER_STATUS -> throw TooEarlyVote()
//                    TOO_LATE_STATUS -> throw TooLateVote()
//                    HttpStatusCode.Conflict -> return@validateResponse
//                    HttpStatusCode.Unauthorized -> throw Unauthorized()
//                }
            }
        }
        install(DefaultRequest) {
            url.takeFrom(apiUrl)
        }
    }

    suspend fun registration(
        userName: String,
        password: String,
        flowMessage: (String?) -> Unit,
    ): Boolean {
        val data: BasicApi<String> = try {
            val response = client.post {
                apiUrl("registration")
                jsonHeader()
                setBody(AuthenticateData(
                    userName = userName,
                    password = password
                ))
            }
            if (!response.status.isSuccess()) {
                val data = response.body<BasicApi<Any>>()
                Log.i("APIClient", "registration: data.description")
                flowMessage.invoke(data.description)
                return false
            }
            response.request.date()
            val data = response.body<BasicApi<String>>()
            data
        } catch (e: Exception) {
            Log.i("APIClient", "registration: Exception")
            e.printStackTrace()
            flowMessage.invoke("Exception")
            return false
        }

        updateLocalData {
            it.copy(token = data.data)
        }
        if (getLocalData().token.isNullOrBlank()) {
            return false
        }
        return true
    }

    suspend fun authorization(
        userName: String,
        password: String,
        flowMessage: (String?) -> Unit,
    ): Boolean {
        val data: BasicApi<String> = try {
            val response = client.post {
                apiUrl("authorization")
                jsonHeader()
                setBody(AuthenticateData(
                    userName = userName,
                    password = password
                ))
            }

            if (!response.status.isSuccess()) {
                val data = response.body<BasicApi<Any>>()
                Log.i("APIClient", "authorization: data.description")
                flowMessage.invoke(data.description)
                return false
            }
            response.body()
        } catch (e: Exception) {
            Log.i("APIClient", "authorization: Exception")
            e.printStackTrace()
            flowMessage.invoke("Exception")
            return false
        }

        updateLocalData {
            it.copy(token = data.data)
        }
        if (getLocalData().token.isNullOrBlank()) {
            return false
        }
        return true
    }


    suspend fun click(
        flowMessage: (String?) -> Unit,
        flowSuccess: (UserData?) -> Unit,
    ): Boolean {
        val data: BasicApi<UserData> = try {
            val response = client.put {
                apiUrl("click")
            }
            if (!response.status.isSuccess()) {
                val data = response.body<BasicApi<Any>>()
                Log.i("APIClient", "authorization: data.description")
                flowMessage.invoke(data.description)
                return false
            }
            response.body()
        } catch (e: Exception) {
            Log.i("APIClient", "authorization: Exception")
            e.printStackTrace()
            flowMessage.invoke("Exception")
            return false
        }
        flowSuccess.invoke(data.data)
        return true
    }


    private fun HttpRequestBuilder.jsonHeader() {
        contentType(ContentType.Application.Json)
    }


    private fun HttpRequestBuilder.apiUrl(path: String) {
        header(HttpHeaders.Authorization, "Bearer $token")
        url {
            encodedPath = path
        }
    }

    override fun close() {
        client.close()
    }

    fun deleteLocalData() = runBlocking {
        context.dataStore.updateData { LocalAppSettings() }
    }

    fun updateLocalData(dataPut: (LocalAppSettings) -> LocalAppSettings) = runBlocking {
        context.dataStore.updateData { oldData ->
            dataPut.invoke(oldData)
        }
    }

    fun getLocalData() = runBlocking { context.dataStore.data.first() }
}