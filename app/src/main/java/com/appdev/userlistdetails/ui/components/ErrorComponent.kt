package com.appdev.userlistdetails.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorComponent(
    message: String,
    onRetry: () -> Unit,
    @DrawableRes drawable: Int ,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ImagenComponent(
            modifier = modifier,
            drawable = drawable
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextComponent(
            text = buildAnnotatedString { append(message) },
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        ButtonComponent(
            text = "Reitentar",
            onClick = onRetry,
        )
    }
}
