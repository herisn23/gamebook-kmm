package resources

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import async.mapAsync
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.stringWithContentsOfFile
import platform.posix.memcpy


actual suspend fun Resources.loadStrings(languages: List<String>): Strings =
    languages.mapAsync {
        it to strings(it).read().let(::stringsContentToMap)
    }.toMap()


fun rootContext() = "${NSBundle.mainBundle.resourcePath}/compose-resources"
fun file(dir: String, name: String) = "${rootContext()}/$dir/$name"
fun strings(id: String) = "${rootContext()}/strings/$id.yaml"


@OptIn(ExperimentalForeignApi::class)
private fun String.read(): String =
    NSString.stringWithContentsOfFile(
        this,
        encoding = NSUTF8StringEncoding,
        error = null
    ) ?: error("Couldn't load resource: $this")


actual fun Resources.loadFile(dir: String, name: String): ByteArray? =
    NSData.create(NSURL(fileURLWithPath = file(dir, name)))?.toByteArray()

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    return ByteArray(length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), bytes, length)
        }
    }
}

actual fun Resources.loadFont(name: String): FontFamily =
    Resources.loadFile("fonts", name)!!.createFontFromByteArray(name)

fun ByteArray.createFontFromByteArray(name: String): FontFamily {
    val font = Font(
        identity = name,
        data = this,
        weight = FontWeight.Normal
    )
    return FontFamily(font)
}
