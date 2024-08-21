package story

import androidx.compose.ui.util.fastMap
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile


actual suspend fun loadStoryApi(path: String, fileName: String): String =
    "${storyContext(path)}/${fileName}".read()

actual suspend fun loadStoryLocalization(path: String, strings: String): Map<String, String> =
    "${storyContext(path)}/${strings}".let { path ->
        NSFileManager
            .defaultManager()
            .directoryContentsAtPath(path)
            ?.fastMap {
                val fileName = "$it"
                val lang = fileName.replace(".yaml", "")
                lang to "$path/$it".read()
            }
            ?.toMap() ?: emptyMap()
    }


actual suspend fun loadStoriesApi(fileName: String): String =
    "${rootContext()}/${fileName}".read()

fun rootContext() = "${NSBundle.mainBundle.resourcePath}/compose-resources"
fun storyContext(id: String) = "${rootContext()}/$id"


@OptIn(ExperimentalForeignApi::class)
private fun String.read(): String =
    NSString.stringWithContentsOfFile(
        this,
        encoding = NSUTF8StringEncoding,
        error = null
    ) ?: error("Couldn't load resource: $this")