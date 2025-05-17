package com.example.safetynet

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun LineChart(
    data: List<Pair<Int, Double>> = emptyList(),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(Color.White)
) {
    val spacing = 100f
    val graphColor = Color.Cyan
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.toInt() ?: 0) }
    val density = LocalDensity.current

    val xTextPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.DKGRAY
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
            isAntiAlias = true
        }
    }

    val yTextPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.DKGRAY
            textAlign = Paint.Align.RIGHT
            textSize = density.run { 12.sp.toPx() }
            isAntiAlias = true
        }
    }

    Canvas(modifier = modifier) {
        if (data.isEmpty()) return@Canvas

        val spacePerPoint = (size.width - spacing) / data.size

        // Draw X-axis labels (bottom)
        (data.indices step 2).forEach { i ->
            val x = spacing + i * spacePerPoint
            val label = data[i].first.toString()
            drawContext.canvas.nativeCanvas.drawText(
                label,
                x,
                size.height - 10f, // 10px above bottom
                xTextPaint
            )
        }

        // Draw Y-axis labels (left)
        val step = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            val yVal = round(lowerValue + step * i).toString()
            val y = size.height - spacing - i * (size.height - spacing) / 5f
            drawContext.canvas.nativeCanvas.drawText(
                yVal,
                spacing - 10f,
                y,
                yTextPaint
            )
        }

        // Line path
        val strokePath = Path().apply {
            val height = size.height
            data.forEachIndexed { i, point ->
                val ratio = (point.second - lowerValue) / (upperValue - lowerValue)
                val x = spacing + i * spacePerPoint
                val y = height - spacing - (ratio * height).toFloat()
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        // Fill under the curve
        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(spacing + (data.size - 1) * spacePerPoint, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(transparentGraphColor, Color.Transparent),
                endY = size.height - spacing
            )
        )
    }
}
