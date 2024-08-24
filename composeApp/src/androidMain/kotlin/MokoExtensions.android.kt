import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.core.content.ContextCompat
import dev.icerock.moko.resources.ImageResource

@Composable
actual fun imageResource(imageResource: ImageResource): ImageBitmap =
    ImageBitmap.imageResource(id = imageResource.drawableResId)