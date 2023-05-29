package ru.yo.excenssive_k

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import ru.yo.excenssive_k.util.ModelService
import ru.yo.excenssive_k.theme.RouletteTheme
import ru.yo.excenssive_k.ui.InitController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val service = ModelService(application)

        setContent {
            RouletteTheme {
                InitController(service)
            }
        }
    }
}
