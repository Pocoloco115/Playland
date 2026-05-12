package com.example.playland2.feature.catchfood.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.unit.sp
val JuguetitoFont = FontFamily.Default

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = JuguetitoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 1.sp
    ),
    // Fuente para los btns
    labelLarge = TextStyle(
        fontFamily = JuguetitoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    // Fuente por defecto
    bodyLarge = TextStyle(
        fontFamily = JuguetitoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)