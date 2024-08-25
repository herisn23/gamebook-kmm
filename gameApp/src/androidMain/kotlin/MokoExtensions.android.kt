import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import dev.icerock.moko.resources.ImageResource

@Composable
actual fun imageResource(imageResource: ImageResource): ImageBitmap =
    ImageBitmap.imageResource(id = imageResource.drawableResId)