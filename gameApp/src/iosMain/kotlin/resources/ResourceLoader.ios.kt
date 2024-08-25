package resources

import async.mapAsync
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile


actual suspend fun Resources.loadStrings(languages: List<String>): Strings =
    languages.mapAsync {
        it to strings(it).read().let(::stringsContentToMap)
    }.toMap()


fun rootContext() = "${NSBundle.mainBundle.resourcePath}/compose-resources"
fun strings(id: String) = "${rootContext()}/strings/$id.yaml"

@OptIn(ExperimentalForeignApi::class)
private fun String.read(): String =
    NSString.stringWithContentsOfFile(
        this,
        encoding = NSUTF8StringEncoding,
        error = null
    ) ?: error("Couldn't load resource: $this")