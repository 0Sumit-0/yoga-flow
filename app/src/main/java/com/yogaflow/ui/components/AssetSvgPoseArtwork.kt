package com.yogaflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import coil.size.Scale
import com.yogaflow.ui.theme.SageSubtle


@Composable
fun AssetSvgPoseArtwork(
    iconSvgType: String,
    size: Dp,
    accentColor: Color,
    modifier: Modifier = Modifier
) {

    // Pose SVGs are dense 1500x1500 traces. Parsing them for every list row
    // stalls the whole UI. Keep Coil for hero/player sizes only.
    if (size < 140.dp) {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(SageSubtle),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.SelfImprovement,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(size * 0.52f)
            )
        }
        return
    }

    val context = LocalContext.current
    val sizePx = with(LocalDensity.current) { size.roundToPx() }.coerceIn(1, 640)

    val request = remember(iconSvgType, sizePx) {
        ImageRequest.Builder(context.applicationContext)
            .data("file:///android_asset/yoga_poses/$iconSvgType.svg")
            .decoderFactory(SvgDecoder.Factory())
            .size(sizePx)
            .scale(Scale.FIT)
            .precision(Precision.EXACT)
            .memoryCacheKey("pose_svg_${iconSvgType}_$sizePx")
            .diskCachePolicy(CachePolicy.DISABLED)
            .crossfade(false)
            .build()
    }
    AsyncImage(
        model = request,
        contentDescription = null,
        modifier = modifier.size(size),
        contentScale = ContentScale.Fit,
        // 4. Apply the color tint to the loaded SVG
        colorFilter = ColorFilter.tint(accentColor)
    )
}
