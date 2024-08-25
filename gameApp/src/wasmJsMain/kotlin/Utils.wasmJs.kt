import androidx.compose.ui.graphics.ImageBitmap

actual val platform: Platform
    get() = Platform(
        name = "wasmj",
        version = "123"
    )

actual val ByteArray.bitmap: ImageBitmap?
    get()
    = null