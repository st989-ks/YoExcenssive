package ru.yo.excenssive_k.ui

import androidx.compose.runtime.Composable
import ru.yo.excenssive_k.util.ModelService

@Composable
fun InitController(
    service: ModelService,
) {
    WithAppController(service) {
        Login(it)
    }
}