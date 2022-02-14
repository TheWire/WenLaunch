package com.thewire.wenlaunch.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thewire.wenlaunch.presentation.theme.WenLaunchTheme

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 6.dp,
    padding: PaddingValues = PaddingValues(6.dp),
    headingText: String,
    headingStyle: TextStyle = MaterialTheme.typography.h5,
    headingColor: Color = MaterialTheme.colors.onPrimary,
    headingExtraContent: @Composable RowScope.() -> Unit = {},
    headingSectionColor: Color = MaterialTheme.colors.primary,
    bodyColor: Color = MaterialTheme.colors.surface,
    bodyContent: @Composable ColumnScope.() -> Unit
    ) {
    Card(
        modifier = modifier
            .wrapContentHeight(),
        elevation = elevation,
        backgroundColor = bodyColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .background(color = headingSectionColor)
                    .padding(padding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = headingText,
                    modifier = Modifier
                        .weight(1f),
                    style = headingStyle,
                    color = headingColor
                )
                headingExtraContent()
            }
            Column(modifier = Modifier
                .padding(padding)
                .fillMaxWidth()) {

                bodyContent()
            }
        }
    }
}

@Preview
@Composable
fun TestInfoCard() {
    WenLaunchTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            InfoCard(
                modifier = Modifier,
                headingText = "This is the heading",
                headingExtraContent = {
                    Icon(
                        Icons.Filled.AccountBox,
                        "test icon"
                    )
                }
            ) {
                Text("some text")
                Text("some more text")
                Text("event more text")
            }
        }
    }
}