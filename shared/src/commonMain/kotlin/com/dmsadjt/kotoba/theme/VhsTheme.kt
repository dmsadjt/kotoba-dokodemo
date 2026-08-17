package com.dmsadjt.kotoba.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * "Random Play" VHS-rental-store inspired palette & shapes.
 * Not a pixel clone of any specific game's assets/fonts - an original
 * approximation of the video-store aesthetic (warm paper stock, stamped
 * rental stickers, torn ticket stubs).
 */
object VhsColors {
    val Paper = Color(0xFFF3E8CE)
    val PaperDark = Color(0xFFE6D6AC)
    val Counter = Color(0xFF1E1A14)
    val CounterLight = Color(0xFF2C261C)
    val Ink = Color(0xFF221A11)
    val Red = Color(0xFFC5372B)
    val RedDark = Color(0xFF8F281F)
    val Amber = Color(0xFFDFA236)
    val Teal = Color(0xFF2E6E68)
    val Cream = Color(0xFFFBF4E2)
}

/** Ticket-stub shape: two opposite corners clipped, like a torn rental stub. */
fun ticketShape(cut: Dp = 14.dp): Shape = CutCornerShape(topEnd = cut, bottomStart = cut)

/** Smaller clipped-corner shape for stamp-like elements (buttons, badges). */
fun stampShape(cut: Dp = 8.dp): Shape = CutCornerShape(topEnd = cut, bottomStart = cut)

@Composable
fun VhsSectionHeader(label: String, accent: Color = VhsColors.Red) {
    Box(
        modifier = Modifier
            .background(accent)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label.uppercase(),
            color = VhsColors.Cream,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp,
            fontSize = 13.sp
        )
    }
}

/** A dashed horizontal "tear here" divider. */
@Composable
fun TearDivider(modifier: Modifier = Modifier, color: Color = VhsColors.Ink) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2f),
            end = Offset(size.width, size.height / 2f),
            strokeWidth = 2f,
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
        )
    }
}

/** Thick-outlined ticket-stub panel, the base "card" of the whole theme. */
@Composable
fun VhsCard(
    modifier: Modifier = Modifier,
    accent: Color = VhsColors.Ink,
    fill: Color = VhsColors.Paper,
    shape: Shape = ticketShape(),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(fill, shape)
            .border(BorderStroke(2.dp, accent), shape)
    ) {
        content()
    }
}
