package ru.yo.excenssive_k.util

import android.content.Context
import androidx.datastore.dataStore
import ru.yo.excenssive_k.models.LocalSettingsSerializer

val Context.dataStore by dataStore(
    fileName = "local_settings.json",
    serializer = LocalSettingsSerializer())