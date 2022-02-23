package com.thewire.wenlaunch.presentation.components.layout

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout

@Composable
fun LaunchCardLayout(
    modifier: Modifier = Modifier,
    image: Painter,
    imageDescription: String,
    imageScale: ContentScale = ContentScale.Crop,
    imageModifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            header()
            content()
            Image(
                modifier = imageModifier
                    .animateContentSize()
                    .fillMaxSize(),
                painter = image,
                contentDescription = imageDescription,
                contentScale = imageScale,
            )
        }
    ) { measurables, constraints ->

        val topConstraints = constraints.copy(
            minHeight = measurables[0].minIntrinsicHeight(constraints.maxWidth),
            maxHeight = minOf(
                measurables[0].maxIntrinsicHeight(constraints.minWidth),
                constraints.maxHeight
            )
        )

        val lowerConstraints = constraints.copy(
            minHeight = maxOf(constraints.minHeight - topConstraints.maxHeight, 0),
            maxHeight = constraints.maxHeight - topConstraints.minHeight,
        )


        val aspectRatio = try  {
            image.intrinsicSize.height / image.intrinsicSize.width
        } catch(e: Exception) {
            0.667f
        }
        val imageConstraints = lowerConstraints.copy(
            minWidth = minOf(
                (lowerConstraints.minHeight / aspectRatio).toInt(),
                maxOf(
                    constraints.minWidth - measurables[1].minIntrinsicWidth(lowerConstraints.minHeight),
                    0
                )
            ),
            maxWidth = minOf(
                (lowerConstraints.maxHeight / aspectRatio).toInt(),
                constraints.maxWidth - measurables[1].minIntrinsicWidth(lowerConstraints.maxHeight)
            ),
        )
        val imagePlaceable = measurables[2].measure(imageConstraints)
        val contentConstraints = lowerConstraints.copy(
            minWidth = measurables[1].minIntrinsicWidth(lowerConstraints.minHeight),
            maxWidth = constraints.maxWidth - imagePlaceable.width,
        )

        val contentPlaceable = measurables[1].measure(contentConstraints)


        val headerConstraints = topConstraints.copy(
            maxWidth = maxOf(
                minOf(
                    contentPlaceable.width + imagePlaceable.width,
                    constraints.maxWidth
                ), constraints.minWidth
            )
        )

        val headerPlaceable = measurables[0].measure(headerConstraints)

        layout(
            headerPlaceable.width,
            headerPlaceable.height + imagePlaceable.height
        ) {
            var yPosition = 0
            headerPlaceable.placeRelative(0, 0)
            yPosition += headerPlaceable.height
            contentPlaceable.placeRelative(0, yPosition)
            val imageYOffset =
                maxOf(headerPlaceable.width - (contentPlaceable.width + imagePlaceable.width), 0)
            imagePlaceable.placeRelative(contentPlaceable.width + imageYOffset, yPosition)
        }
    }
}