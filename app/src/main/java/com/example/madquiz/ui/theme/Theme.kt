package com.example.madquiz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = darkColorScheme(
    primary        = NewsRed,
    onPrimary      = Color.White,
    secondary      = NewsRedDark,
    onSecondary    = Color.White,
    background     = DarkBg,
    onBackground   = TextPrimary,
    surface        = CardBg,
    onSurface      = TextPrimary,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = TextSecondary,
    outline        = DividerColor,
)

@Composable
fun MADQUIZTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography  = Typography,
        content     = content
    )
}
