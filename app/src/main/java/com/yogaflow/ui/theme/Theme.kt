package com.yogaflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = SageLight,
    onPrimary = CharcoalBlack,
    primaryContainer = SageDark,
    onPrimaryContainer = SageSubtle,
    secondary = SandTertiary,
    onSecondary = PureWhite,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = SandContainer,
    tertiary = WarmClay,
    background = DarkBg,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder,
    outlineVariant = DarkSurfaceVariant,
)

private val LightColorScheme = lightColorScheme(
    primary = SagePrimary,
    onPrimary = PureWhite,
    primaryContainer = SagePrimaryContainer,
    onPrimaryContainer = SageOnPrimaryContainer,
    secondary = CharcoalDark,
    onSecondary = PureWhite,
    secondaryContainer = SageSubtle,
    onSecondaryContainer = SageDark,
    tertiary = SandTertiary,
    onTertiary = PureWhite,
    tertiaryContainer = SandContainer,
    onTertiaryContainer = SandOnContainer,
    background = WarmIvory,
    onBackground = CharcoalBlack,
    surface = PureWhite,
    onSurface = CharcoalBlack,
    surfaceVariant = LinenSurface,
    onSurfaceVariant = CharcoalMuted,
    outline = BorderSubtle,
    outlineVariant = LinenSurface
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
//    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
