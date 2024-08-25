import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import platform.UIKit.UIDevice

actual val ByteArray.bitmap: ImageBitmap?
    get() = Image.makeFromEncoded(this).toComposeImageBitmap()


actual val platform: Platform = object : Platform {
    override val name: String by lazy {
        UIDevice.currentDevice.systemName()
    }
    override val version: String by lazy {
        UIDevice.currentDevice.systemVersion
    }

}