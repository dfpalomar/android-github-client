package com.playground.android.githubclient.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
  primary = Color.White,
  primaryVariant = Color.Gray,
  secondary = Color.Cyan,
  onPrimary = Color.Black
)

private val LightColorPalette = lightColors(
  primary = Color.Black,
  primaryVariant = Color.Gray,
  secondary = Color.Cyan
)

@Composable
fun GithubClientTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }
  MaterialTheme(
    colors = colors,
    content = content
  )
}