package io.github.henryquan.battery68.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.henryquan.battery68.ui.theme.Battery68Theme

@Composable
fun BatteryStatusView(viewSize: Float) {
    val radius = viewSize / 2
    val animateFloat = remember { Animatable(0f) }

    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 30000, easing = LinearEasing)
        )
    }

    Canvas(modifier = Modifier.size(viewSize.dp)) {
        drawArc(
            color = Color.Gray,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(viewSize / 4, viewSize / 4),
            size = Size(radius * 2, radius * 2),
            style = Stroke(20.0f, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color.Black,
            startAngle = 0f,
            sweepAngle = 360f * animateFloat.value,
            useCenter = false,
            topLeft = Offset(viewSize / 4, viewSize / 4),
            size = Size(radius * 2, radius * 2),
            style = Stroke(10.0f, cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Battery68Theme {
        BatteryStatusView(100.0f)
    }
}