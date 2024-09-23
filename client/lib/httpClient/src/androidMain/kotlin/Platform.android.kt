import android.os.Build

actual val platform: Platform =
    Platform(
        "android",
        Build.VERSION.SDK_INT.toString()

    )