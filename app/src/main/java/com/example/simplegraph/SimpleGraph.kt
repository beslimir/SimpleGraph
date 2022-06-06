package com.example.simplegraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp

@Composable
fun SimpleGraph(
    modifier: Modifier = Modifier,
    range: List<Int> = emptyList()
) {
    val spacing = 100f
    val upperValue = remember(range) {
        range.maxOfOrNull { it.plus(1) } ?: 0
    }
    val lowerValue = remember(range) {
        range.minOfOrNull { it } ?: 0
    }
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val perHour = (size.width - spacing) / range.size
        (range.indices).forEach { i ->
            val currentValue = range[i]
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    currentValue.toString(),
                    spacing + i * perHour,
                    size.height - 5,
                    textPaint
                )
                Log.d("myGraph", "x text:${spacing + i * perHour}" +
                        "\nperHour: $perHour" +
                        "\nsize width: ${size.width}"
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    (lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
                Log.d("myGraph", "value:${(lowerValue + priceStep * i)}" +
                        "\npriceStep: $priceStep" +
                        "\nsize height: ${size.height}"
                )
            }
        }
    }

}