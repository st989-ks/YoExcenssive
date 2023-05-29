package ru.yo.excenssive_k.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.yo.excenssive_k.MainActivity
import ru.yo.excenssive_k.util.BackPressHandler
import ru.yo.excenssive_k.util.ModelService

typealias View = @Composable (AppController) -> Unit

@Composable
fun WithAppController(
    service: ModelService,
    default: @Composable (AppController) -> Unit
) {
    val controller = remember { AppController(service, default) }
    LaunchedEffect(key1 = Unit, block = {
        service.click {
            when (it) {
                true -> controller.showMainScreen()
                false -> controller.showLogin()
            }
        }
    })
    BackPressHandler { controller.back() }
    val last by controller.last.collectAsState()
    last(controller)
}

class AppController(
    private val service: ModelService,
    val default: @Composable (AppController) -> Unit = {}
) {
    private val stack = mutableListOf<View>()
    val last: MutableStateFlow<View> = MutableStateFlow(default)

    val isSignUser = service.isSignUser

    fun push(item: @Composable (AppController) -> Unit) {
        stack.add(item)
        last.value = item
    }

    fun back() {
        if (stack.isEmpty()) {
            (service.context as MainActivity).finish()
            return
        }
        stack.removeLast()
        last.value = stack.lastOrNull() ?: default
    }


    fun click() {
        GlobalScope.launch {}
        service.click()
    }

    fun registration(
        userName: String,
        password: String
    ) {
        GlobalScope.launch {}
        service.registration(
            userName = userName,
            password = password) {
            showMainScreen()
        }
    }

    fun authorization(
        userName: String,
        password: String
    ) {
        GlobalScope.launch {}
        service.authorization(
            userName = userName,
            password = password) {
            showMainScreen()
        }
    }

    fun showRegistration() = push { Registration(this) }
    fun showLogin() = push { Login(this) }
    fun showMainScreen() = push {
        val clickableValue by service.clickValue.collectAsState()
        MainScreen(this,clickableValue ) }
}

