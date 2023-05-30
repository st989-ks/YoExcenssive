package ru.yo.excenssive_k.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun push(item: @Composable (AppController) -> Unit) {
        stack.add(item)
        last.value = item
    }

    fun back() {
        if (stack.isEmpty()) {
            (service.context as Activity).finish()
            return
        }
        stack.removeLast()
        last.value = stack.lastOrNull() ?: default
    }

    fun click() {
        service.click()
    }

    fun registration(
        userName: String,
        password: String
    ) {
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
        service.authorization(
            userName = userName,
            password = password) {
            showMainScreen()
        }
    }

    fun showRegistration() = push {
        Registration(registration = { login, password ->
            registration(
                userName = login,
                password = password
            )
        })
    }

    fun showLogin() = push {

        Login(authorization = { login, password ->
            authorization(
                userName = login,
                password = password
            )
        },
            goToRegistration = ::showRegistration
        )
    }

    fun showSplashScreen() = push { Splash() }
    fun showMainScreen() = push {
        val clickableValue by service.clickValue.collectAsState()
        MainScreen(
            onClickBigButton = ::click,
            clickValue= clickableValue)
    }
}

