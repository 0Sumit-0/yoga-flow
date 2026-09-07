import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun AssetSvgPoseArtwork(
    iconSvgType: String, // e.g., "balancing_tree_pose"
    size: Dp,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // 1. Construct the path based on the filename in your screenshot
    val assetPath = "yoga_poses/$iconSvgType.svg"

    // 2. Build the Coil ImageRequest
    // We must use 'file:///android_asset/' prefix to tell Coil to look in the assets folder.
    val model = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/$assetPath")
            .decoderFactory(SvgDecoder.Factory()) // Crucial: Tells Coil to render the SVG file
            .build()
    )

    // 3. Display the image
    Image(
        painter = model,
        contentDescription = null, // Or use string resource for accessibility
        modifier = modifier.size(size),
        contentScale = ContentScale.Fit,
        // 4. Apply the color tint to the loaded SVG
        colorFilter = ColorFilter.tint(accentColor)
    )
}