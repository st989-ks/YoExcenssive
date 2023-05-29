package ru.yo.excenssive_k.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateData(
    val password: String?,
    val userName: String?,
)

@Serializable
data class BasicApi<T> (
    val data: T?,
    val status: HttpAppStatus?,
    val description: String?,
)

@Serializable
data class HttpAppStatus(
      val value: Int?,
    val description: String?
)

@Serializable
data class UserData(
         val id: Int?,
        val name: String?,
        val phone: String?,
        val clickValues: Int?,
        val clickList: List<Int>?,
)
