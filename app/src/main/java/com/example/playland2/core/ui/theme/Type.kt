package com.example.playland2.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playland2.R

val MinecraftFont = FontFamily(
    Font(R.font.minecraft, FontWeight.Normal)
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = MinecraftFont,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MinecraftFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MinecraftFont,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MinecraftFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MinecraftFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
