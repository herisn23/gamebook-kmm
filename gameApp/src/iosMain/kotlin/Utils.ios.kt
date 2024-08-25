import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import platform.UIKit.UIDevice

actual val ByteArray.bitmap: ImageBitmap?
    get() = Image.makeFromEncoded(this).toComposeImageBitmap()


actual val platform: Platform =
    Platform(
        UIDevice.currentDevice.systemName(),
        UIDevice.currentDevice.systemVersion
    )