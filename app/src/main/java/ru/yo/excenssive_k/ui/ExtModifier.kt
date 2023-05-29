package ru.yo.excenssive_k.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color

fun Modifier.clickableRipple(
    enabled: Boolean = true,
    indicationBounded:  Boolean = true,
    onClick: () -> Unit) = composed {
    clickable(
        onClick = onClick,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(indicationBounded, color = Color.Red)
    )
}