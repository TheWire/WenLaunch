package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WebcastLiveIndicator(
    modifier: Modifier = Modifier,
    text: String,
    uri: Uri?,
    fontSize: TextUnit = 14.sp,
    contentPadding: PaddingValues = PaddingValues(0.dp, 0.dp),
){
    Button(
        onClick = {},
        modifier = modifier,
        colors = buttonColors(Color.Red, Color.White),
        contentPadding = contentPadding,
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "play livestream",
            modifier = Modifier,
        )
        Text(
            modifier = Modifier,
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}