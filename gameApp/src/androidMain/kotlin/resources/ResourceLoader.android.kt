package resources

import async.mapAsync
import cz.roldy.gb.context

actual suspend fun Resources.loadStrings(languages: List<String>): Strings =
    languages.mapAsync {
        it to "strings/$it.yaml".read().let(::stringsContentToMap)
    }.toMap()


private fun String.read(): String =
    context().classLoader?.getResource(this)?.readText()
        ?: error("Couldn't load resource: $this")