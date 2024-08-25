package resources

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import async.mapAsync
import cz.roldy.gb.MainActivity
import cz.roldy.gb.context
import java.io.File
import java.io.FileOutputStream
import java.net.URL

actual suspend fun Resources.loadStrings(languages: List<String>): Strings =
    languages.mapAsync {
        it to "strings/$it.yaml".read().readText().let(::stringsContentToMap)
    }.toMap()


private fun String.read(): URL =
    MainActivity::class.java.classLoader!!.getResource(this)
        ?: error("Couldn't load resource: $this")

actual fun Resources.loadFile(dir: String, name: String): ByteArray? =
    "$dir/$name".read().readBytes()

actual fun Resources.loadFont(name: String): FontFamily =
    loadFile("fonts", name)?.let { byteA ->
        val tempFile = File(context().cacheDir, name)
        FileOutputStream(tempFile).use { it.write(byteA) }
        FontFamily(Font(tempFile, FontWeight.Normal))
    } ?: FontFamily.Serif
