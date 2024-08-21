package story

import cz.roldy.gb.MainActivity

actual suspend fun loadStoryApi(path: String, fileName: String): String =
    "${storyContext(path)}/${fileName}".read()

actual suspend fun loadStoryLocalization(path: String, strings: String): Map<String, String> =
    "${storyContext(path)}/$strings".let { path ->
        val cs = MainActivity::class.java.classLoader?.getResource("$path/cs.yaml")?.readText()!!
        val en = MainActivity::class.java.classLoader?.getResource("$path/en.yaml")?.readText()!!
        mapOf(
            "cs" to cs,
            "en" to en
        )
    }


actual suspend fun loadStoriesApi(fileName: String): String =
    fileName.read()

fun storyContext(path: String) = path


private fun String.read(): String =
    MainActivity::class.java.classLoader?.getResource(this)?.readText()
        ?: error("Couldn't load resource: $this")
