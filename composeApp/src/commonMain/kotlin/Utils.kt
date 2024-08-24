import androidx.compose.ui.graphics.ImageBitmap

inline fun <reified T> Any?.cast(): T {
    this is T
    return this as T
}

interface Platform {
    val name: String
    val version:String
}

expect val platform: Platform

expect val ByteArray.bitmap: ImageBitmap?

