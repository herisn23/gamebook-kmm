import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter


expect val ByteArray.bitmap: ImageBitmap?
val ByteArray.painter: Painter? get() = bitmap?.let(::BitmapPainter)

