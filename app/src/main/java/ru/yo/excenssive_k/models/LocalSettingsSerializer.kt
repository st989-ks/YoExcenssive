package ru.yo.excenssive_k.models

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.json.Json

class LocalSettingsSerializer(
) : Serializer<LocalAppSettings> {

    override val defaultValue: LocalAppSettings get() = LocalAppSettings()

    override suspend fun readFrom(input: InputStream): LocalAppSettings {
        return try {
            val text = input.reader().use { reader ->
                reader.readText()
            }

            Json.decodeFromString(deserializer = LocalAppSettings.serializer(), string = text)

        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: LocalAppSettings, output: OutputStream) {
        try {
            val json = Json.encodeToString(
                serializer = LocalAppSettings.serializer(),
                value = t).encodeToByteArray()

            output.also {
                it.write(json)
            }

        } catch (e: SerializationException) {
            e.printStackTrace()
        }
    }
}

