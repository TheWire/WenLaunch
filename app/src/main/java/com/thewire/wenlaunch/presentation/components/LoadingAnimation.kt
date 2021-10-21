package com.thewire.wenlaunch.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.rotationMatrix
import com.thewire.wenlaunch.R

@Composable
fun LoadingAnimation() {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(6.dp)
                    .zIndex(2.0f),
                text = "Loading...",
                style = MaterialTheme.typography.h6

            )
            val rocketWidth = 250.dp
            val rocketHeight = 250.dp
            val infiniteTransition = rememberInfiniteTransition()

            val exhaustImage = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            )

            val yPos = infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        delayMillis = 800,
                        durationMillis = 1800,
                        easing = LinearEasing
                    )
                )
            )

            val rocketImage = if(exhaustImage.value <= 0.5f) {
                painterResource(R.drawable.rocket1)
            } else {
                painterResource(R.drawable.rocket2)
            }

            val modifier = Modifier.offset(
                x = (maxWidth - rocketWidth) / 2,
                y = (maxHeight + rocketHeight) - ((maxHeight + (rocketHeight*2)) * yPos.value)
            )

            Image(
                modifier = modifier
                    .rotate(-35.0f)
                    .width(rocketWidth)
                    .height(rocketHeight)
                    .zIndex(1f),
                painter = rocketImage,
                contentDescription = "rocket animation"
            )
        }

}