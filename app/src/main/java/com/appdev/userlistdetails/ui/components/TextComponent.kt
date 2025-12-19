package com.appdev.userlistdetails.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    style: TextStyle = TextStyle.Default,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onSurface,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        style = style,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        overflow = overflow
    )
}

@Preview(showBackground = true)
@Composable
fun TextComponentPreview() {
    TextComponent(
        text = buildAnnotatedString { append("Hello World") },
        color = Color.Black
    )
}