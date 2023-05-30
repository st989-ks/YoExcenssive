package ru.yo.excenssive_k.ui

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.yo.excenssive_k.R
import ru.yo.excenssive_k.theme.DimApp

@Composable
fun MainScreen(
    onClickBigButton: ()-> Unit,
    clickValue: Int) {
    val des = LocalDensity.current
    var isButtonSize by remember { mutableStateOf(1f) }
    val weightSizeButton = animateFloatAsState(targetValue = isButtonSize)
    var paddingButton by remember { mutableStateOf(0.dp) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .systemBarsPadding(),
    ) {

        Column(
            modifier = Modifier
                .onGloballyPositioned { layoutCoordinates ->
                    paddingButton =  with(des){layoutCoordinates.size.height.toDp()}
                }
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(DimApp.screenPadding),
                text = "Жми на кнопку",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                modifier = Modifier.padding(DimApp.screenPadding),
                text = clickValue.toString(),
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.displayLarge
            )
        }

        Box(modifier = Modifier
            .align(Alignment.Center)
            .padding(vertical = paddingButton)
            .padding(DimApp.screenPadding*2)
            .scale(weightSizeButton.value)
            .clip(CircleShape)
            .clickableRipple {  }
            .pointerInput(Unit) {
                awaitEachGesture {
                    MotionEvent.ACTION_DOWN
                    do {
                        val event = awaitPointerEvent()
                        isButtonSize = 0.9f
                    } while (event.changes.any { it.pressed })
                    isButtonSize = 1f
                    onClickBigButton.invoke()
                }
            }.paint(
                painter = painterResource(id = R.drawable.button_one)
            )
        )

    }
}