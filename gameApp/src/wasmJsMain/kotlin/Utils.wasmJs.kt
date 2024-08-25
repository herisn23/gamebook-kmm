import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual val platform: Platform
    get() = Platform(
        name = "wasmj",
        version = "123"
    )

actual val ByteArray.bitmap: ImageBitmap?
    get() =
        Image.makeFromEncoded(this).toComposeImageBitmap()