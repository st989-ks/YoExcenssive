package ru.yo.excenssive_k

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import ru.yo.excenssive_k.ui.theme.RouletteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            RouletteTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.5f))
                        .systemBarsPadding(),
                ) {

                }
            }
        }
    }
}
