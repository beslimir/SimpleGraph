package com.example.simplegraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SimpleGraph(
    modifier: Modifier = Modifier,
    viewModel: NumberGenerator = hiltViewModel()
) {

    val state = viewModel.state

    val spacing = 100f
    val upperValue = remember(state.generatedList) {
        state.generatedList.maxOfOrNull { it.plus(1) } ?: 0
    }
    val lowerValue = remember(state.generatedList) {
        state.generatedList.minOfOrNull { it } ?: 0
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
        //horizontal
        val horizontalLine = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val perHour = (size.width - spacing) / horizontalLine.size
        (horizontalLine.indices).forEach { i ->
            val currentValue = horizontalLine[i]
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

        //vertical
        val priceStep = 2
        (1..10 step 2).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    i.toString(),
                    30f,
                    size.height - i * size.height / 10,
                    textPaint
                )
                Log.d("myGraph", "value:${(lowerValue + priceStep * i)}" +
                        "\npriceStep: $priceStep" +
                        "\nsize height: ${size.height}"
                )
            }
        }

        val strokePath = Path().apply {
            val height = size.height
            for (i in state.generatedList.indices) {
                val currValue = state.generatedList[i]
                val nextValue = state.generatedList.getOrNull(i + 1) ?: state.generatedList.last() + 1

                val x1 = spacing + i * perHour
                val y1 = height - currValue * (height / 10)
                val x2 = spacing + (i + 1) * perHour
                val y2 = height - nextValue * (height / 10)

                if (i == 0) {
                    moveTo(x1, y1)
                }
                val lastX = (x1 + x2) / 2f
                val lastY = (y1 + y2) / 2f
                quadraticBezierTo(x1, y1, x2, y2)
//                quadraticBezierTo(
//                    x1, y1, lastX, lastY
//                )

                Log.d("points", "x1: $x1 y1: $y1")
            }
        }

        drawPath(
            path = strokePath,
            color = Color.Blue,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }

}