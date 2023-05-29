package ru.yo.excenssive_k.models

import kotlinx.serialization.Serializable

@Serializable
data class LocalAppSettings(
    val token: String? = null
)
