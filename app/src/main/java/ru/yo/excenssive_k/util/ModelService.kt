package ru.yo.excenssive_k.util

import android.content.Context
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.yo.excenssive_k.data.APIClient
import kotlin.coroutines.CoroutineContext

class ModelService(
    val context: Context,
) : CoroutineScope, Closeable {

    private val exceptionHandler: CoroutineExceptionHandler = object : CoroutineExceptionHandler {
        override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

        override fun handleException(context: CoroutineContext, exception: Throwable) {
            println("Failed with exception: ${exception.message}")
            exception.printStackTrace()
        }
    }

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + exceptionHandler + Dispatchers.IO

    private val client: APIClient by lazy {
        APIClient(context = context)
    }

    private val _isSignUser = MutableStateFlow(false)
    val isSignUser = _isSignUser.asStateFlow()

    private val _clickValue = MutableStateFlow(0)
    val clickValue = _clickValue.asStateFlow()

    fun registration(
        userName: String,
        password: String,
        inSuccess:()->Unit
    ) {
        launch {
            _isSignUser.value = client.registration(
                userName = userName,
                password = password,
                flowMessage = {

                }
            )
            if (_isSignUser.value){
                inSuccess.invoke()
            }
        }
    }

    fun authorization(
        userName: String,
        password: String,
        inSuccess:()->Unit
    ) {
        launch {
            _isSignUser.value = client.authorization(
                userName = userName,
                password = password,
                flowMessage = {

                }
            )
            if (_isSignUser.value){
                inSuccess.invoke()
            }

        }
    }

    fun click(isSuccess: (Boolean) -> Unit = {}) {
        launch {
            client.click(
                flowMessage = {
                },
                flowSuccess = {
                    _isSignUser.value = it?.clickValues != null
                    isSuccess.invoke(_isSignUser.value)
                    _clickValue.value = it?.clickValues ?: 0
                }
            )
        }
    }

    override fun close() {
        client.close()
    }

}