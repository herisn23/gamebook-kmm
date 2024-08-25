import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual val ByteArray.bitmap: ImageBitmap?
    get() = BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()


actual val platform: Platform =
    Platform(
        "android",
        Build.VERSION.SDK_INT.toString()

    )