package com.crxapplications.cardform.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorScheme.disabled: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Gray20 else Gray20