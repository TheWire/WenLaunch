package com.thewire.wenlaunch.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.thewire.wenlaunch.R

private val Orbitron = FontFamily(
    Font(R.font.orbitron_regular, FontWeight.W400),
    Font(R.font.orbitron_medium, FontWeight.W500),
    Font(R.font.orbitron_semibold, FontWeight.W600),
    Font(R.font.orbitron_bold, FontWeight.W700),
    Font(R.font.orbitron_extrabold, FontWeight.W800),
    Font(R.font.orbitron_black, FontWeight.W900),

)

private val Jura = FontFamily(
    Font(R.font.jura_light, FontWeight.W300),
    Font(R.font.jura_regular, FontWeight.W400),
    Font(R.font.jura_medium, FontWeight.W500),
    Font(R.font.jura_bold, FontWeight.W700),

)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Jura,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Jura,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Jura,
        fontWeight = FontWeight.Thin,
        fontSize = 12.sp
    ),
    h4 = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.ExtraBold,
        fontSize= 24.sp,
        letterSpacing = 4.sp
    ),
    h5 = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.Bold,
        fontSize= 20.sp,
        letterSpacing = 3.sp
    ),
    h6 = TextStyle(
        fontFamily = Orbitron,
        fontWeight = FontWeight.Medium,
        fontSize= 18.sp,
        letterSpacing = 2.sp
    )

)
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */