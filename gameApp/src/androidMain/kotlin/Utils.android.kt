import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual val ByteArray.bitmap: ImageBitmap?
    get() = BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()