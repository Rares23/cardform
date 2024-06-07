package com.crxapplications.cardform.ui.core.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class SliceShape(
    private val clipPercent: Float = 0f, // value between 0 and 1
    private val angle: Float = 80f,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Generic(
            Path().apply {
                val leftTopPoint = clipPercent * (size.width + angle)
                moveTo(leftTopPoint, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo((leftTopPoint - angle), size.height)
                close()
            })
    }
}