package com.thewire.wenlaunch.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WebcastLiveIndicator(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    fontSize: TextUnit = 14.sp,
    contentPadding: PaddingValues = PaddingValues(0.dp, 0.dp),
){
    Button(
        onClick = onClick,
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