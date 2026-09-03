package com.yogaflow.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yogaflow.ui.theme.SagePrimary
import com.yogaflow.ui.theme.SageSubtle
import com.yogaflow.ui.theme.SandContainer

@Composable
fun CalmPoseArtwork(
    iconType: String,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    accentColor: Color = SagePrimary
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        SageSubtle,
                        SandContainer.copy(alpha = 0.5f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height
            val strokeColor = accentColor
            val softFill = accentColor.copy(alpha = 0.18f)

            when (iconType.lowercase()) {
                "inversion" -> drawInversionPose(w, h, strokeColor, softFill)
                "standing" -> drawWarriorPose(w, h, strokeColor, softFill)
                "balance" -> drawTreePose(w, h, strokeColor, softFill)
                "forward_bend" -> drawForwardBend(w, h, strokeColor, softFill)
                "backbend" -> drawBackbendPose(w, h, strokeColor, softFill)
                "twist" -> drawTwistPose(w, h, strokeColor, softFill)
                "restorative" -> drawRestorativePose(w, h, strokeColor, softFill)
                else -> drawLotusPose(w, h, strokeColor, softFill)
            }
        }
    }
}

private fun DrawScope.drawLotusPose(w: Float, h: Float, stroke: Color, fill: Color) {
    // Gentle aura ring
    drawCircle(
        color = fill,
        radius = w * 0.35f,
        center = Offset(w * 0.5f, h * 0.52f)
    )
    // Head
    drawCircle(
        color = stroke,
        radius = w * 0.08f,
        center = Offset(w * 0.5f, h * 0.30f)
    )
    // Torso & Arms
    val bodyPath = Path().apply {
        moveTo(w * 0.5f, h * 0.38f)
        lineTo(w * 0.5f, h * 0.62f)
        moveTo(w * 0.32f, h * 0.52f)
        quadraticBezierTo(w * 0.5f, h * 0.60f, w * 0.68f, h * 0.52f)
    }
    drawPath(
        path = bodyPath,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
    // Lotus crossed base
    val base = Path().apply {
        moveTo(w * 0.25f, h * 0.70f)
        quadraticBezierTo(w * 0.5f, h * 0.76f, w * 0.75f, h * 0.70f)
    }
    drawPath(
        path = base,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawInversionPose(w: Float, h: Float, stroke: Color, fill: Color) {
    // Inverted V (Downward Dog)
    drawCircle(
        color = fill,
        radius = w * 0.32f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    val path = Path().apply {
        // Hands
        moveTo(w * 0.24f, h * 0.72f)
        // Arms to Hips (peak)
        lineTo(w * 0.50f, h * 0.30f)
        // Legs to feet
        lineTo(w * 0.76f, h * 0.72f)
    }
    drawPath(
        path = path,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
    // Head alignment
    drawCircle(
        color = stroke,
        radius = w * 0.06f,
        center = Offset(w * 0.35f, h * 0.52f)
    )
}

private fun DrawScope.drawWarriorPose(w: Float, h: Float, stroke: Color, fill: Color) {
    drawCircle(
        color = fill,
        radius = w * 0.32f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    // Head
    drawCircle(
        color = stroke,
        radius = w * 0.07f,
        center = Offset(w * 0.48f, h * 0.26f)
    )
    val warriorPath = Path().apply {
        // Arms horizontal
        moveTo(w * 0.20f, h * 0.38f)
        lineTo(w * 0.80f, h * 0.38f)
        // Torso
        moveTo(w * 0.48f, h * 0.34f)
        lineTo(w * 0.48f, h * 0.55f)
        // Front bent leg
        moveTo(w * 0.48f, h * 0.55f)
        lineTo(w * 0.30f, h * 0.55f)
        lineTo(w * 0.30f, h * 0.75f)
        // Back straight leg
        moveTo(w * 0.48f, h * 0.55f)
        lineTo(w * 0.72f, h * 0.75f)
    }
    drawPath(
        path = warriorPath,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}

private fun DrawScope.drawTreePose(w: Float, h: Float, stroke: Color, fill: Color) {
    drawCircle(
        color = fill,
        radius = w * 0.34f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    // Head
    drawCircle(
        color = stroke,
        radius = w * 0.07f,
        center = Offset(w * 0.50f, h * 0.22f)
    )
    // Standing leg & torso
    val body = Path().apply {
        moveTo(w * 0.50f, h * 0.29f)
        lineTo(w * 0.50f, h * 0.78f)
        // Bent tree leg
        moveTo(w * 0.50f, h * 0.55f)
        lineTo(w * 0.32f, h * 0.48f)
        lineTo(w * 0.50f, h * 0.42f)
        // Anjali mudra hands
        moveTo(w * 0.40f, h * 0.35f)
        lineTo(w * 0.50f, h * 0.33f)
        lineTo(w * 0.60f, h * 0.35f)
    }
    drawPath(
        path = body,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}

private fun DrawScope.drawForwardBend(w: Float, h: Float, stroke: Color, fill: Color) {
    drawCircle(
        color = fill,
        radius = w * 0.32f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    val path = Path().apply {
        // Legs flat on floor
        moveTo(w * 0.75f, h * 0.68f)
        lineTo(w * 0.30f, h * 0.68f)
        // Folded Torso arching over legs
        quadraticBezierTo(w * 0.32f, h * 0.45f, w * 0.68f, h * 0.60f)
    }
    drawPath(
        path = path,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
    // Head resting near knees
    drawCircle(
        color = stroke,
        radius = w * 0.065f,
        center = Offset(w * 0.65f, h * 0.53f)
    )
}

private fun DrawScope.drawBackbendPose(w: Float, h: Float, stroke: Color, fill: Color) {
    drawCircle(
        color = fill,
        radius = w * 0.32f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    val arch = Path().apply {
        // Arch bridge or cobra
        moveTo(w * 0.22f, h * 0.68f)
        quadraticBezierTo(w * 0.50f, h * 0.28f, w * 0.78f, h * 0.68f)
    }
    drawPath(
        path = arch,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round)
    )
    drawCircle(
        color = stroke,
        radius = w * 0.065f,
        center = Offset(w * 0.50f, h * 0.32f)
    )
}

private fun DrawScope.drawTwistPose(w: Float, h: Float, stroke: Color, fill: Color) {
    drawCircle(
        color = fill,
        radius = w * 0.34f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    val twist = Path().apply {
        moveTo(w * 0.28f, h * 0.65f)
        cubicTo(w * 0.45f, h * 0.75f, w * 0.55f, h * 0.35f, w * 0.72f, h * 0.45f)
    }
    drawPath(
        path = twist,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round)
    )
    drawCircle(
        color = stroke,
        radius = w * 0.07f,
        center = Offset(w * 0.65f, h * 0.32f)
    )
}

private fun DrawScope.drawRestorativePose(w: Float, h: Float, stroke: Color, fill: Color) {
    drawCircle(
        color = fill,
        radius = w * 0.35f,
        center = Offset(w * 0.5f, h * 0.5f)
    )
    val wave = Path().apply {
        // Reclined restful wave
        moveTo(w * 0.20f, h * 0.62f)
        quadraticBezierTo(w * 0.40f, h * 0.50f, w * 0.58f, h * 0.64f)
        lineTo(w * 0.80f, h * 0.64f)
    }
    drawPath(
        path = wave,
        color = stroke,
        style = Stroke(width = w * 0.045f, cap = StrokeCap.Round)
    )
    drawCircle(
        color = stroke,
        radius = w * 0.065f,
        center = Offset(w * 0.28f, h * 0.52f)
    )
}
