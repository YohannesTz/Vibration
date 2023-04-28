package com.github.yohannes.vibration.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.Surface

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsListItem(
    settingsIcon: @Composable (() -> Unit)?,
    backgroundColor: Color,
    tintColor: Color,
    title: String,
    subtitle: String?,
    onItemClicked: () -> Unit,
    horizontalPadding: Dp,
    verticalPadding: Dp
) {
    Surface(
        onClick = onItemClicked,
        color = backgroundColor,
        contentColor = tintColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding, vertical = verticalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (settingsIcon != null) {
                settingsIcon()
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h6
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1
                    )
                }
            }
        }
    }
}