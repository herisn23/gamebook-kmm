import androidx.compose.ui.graphics.ImageBitmap

inline fun <reified T> Any?.cast(): T {
    this is T
    return this as T
}


expect val ByteArray.bitmap: ImageBitmap

