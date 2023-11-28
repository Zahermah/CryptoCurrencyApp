package com.example.crypto.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.crypto.R

// Set of Material typography styles to start with

val Ubuntu = FontFamily(
    Font(R.font.ubuntu, FontWeight.Black, FontStyle.Normal),
    Font(R.font.ubuntu_italic, FontWeight.Black, FontStyle.Italic),

    Font(R.font.ubuntu_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.ubuntu_bold_italic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.ubuntu_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.ubuntu_medium_italic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.ubuntu_regular, FontWeight.Normal, FontStyle.Normal),

    Font(R.font.ubuntu_light, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.ubuntu_light_italic, FontWeight.Light, FontStyle.Italic)
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)