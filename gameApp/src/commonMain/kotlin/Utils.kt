import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

inline fun <reified T> Any?.cast(): T {
    this is T
    return this as T
}

data class Platform(
    val name: String,
    val version:String
)

expect val platform: Platform

expect val ByteArray.bitmap: ImageBitmap?
val ByteArray.painter: Painter? get() = bitmap?.let(::BitmapPainter)

