package ru.yo.excenssive_k.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import ru.yo.excenssive_k.theme.DimApp

@Composable
fun Registration(
    registration: (String, String) -> Unit
) {
    var login by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.5f))
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(DimApp.screenPadding),
                text = "Регистрация",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TextField(
                modifier = Modifier.padding(DimApp.screenPadding),
                value = login,
                placeholder = {
                    Text(text = "Логин")
                },
                onValueChange = { login = it })

            TextField(
                modifier = Modifier.padding(DimApp.screenPadding),
                value = password,
                placeholder = {
                    Text(text = "Пароль")
                },
                onValueChange = { password = it })

            Box(modifier = Modifier.size(DimApp.screenPadding))

            Button(onClick = {
                registration.invoke(login.text, password.text)
            }) {
                Text("Давай регистрироваться")
            }
        }
        Box(modifier = Modifier.size(DimApp.screenPadding))
        Box(modifier = Modifier.size(DimApp.screenPadding))
    }
}