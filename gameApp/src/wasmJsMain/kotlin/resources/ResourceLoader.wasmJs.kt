package resources

import androidx.compose.ui.text.font.FontFamily

actual fun Resources.loadFile(
    dir: String,
    name: String
): ByteArray? {
    TODO("Not yet implemented")
}

actual fun Resources.loadFont(name: String): FontFamily {
    TODO("Not yet implemented")
}

actual suspend fun Resources.loadStrings(languages: List<String>): Strings = emptyMap()