package engine.localization

import engine.StoryEngine
import engine.localization.handler.composite
import engine.localization.handler.inflection
import engine.localization.handler.selection
import engine.localization.handler.simple
import story.model.LocalizedString
import story.model.StoryApi

typealias ArgumentResolver = (CompositeLocalizedString.Selector) -> String

operator fun LocalizedString.invoke(delegate: ArgumentResolver? = null) =
    LocalizedStringProxy(this, delegate)

operator fun LocalizedString.invoke(vararg suffixes: String) =
    (arrayOf(key) + suffixes).joinToString("-")

data class LocalizedStringProxy(
    val localizedString: LocalizedString,
    val argumentDelegate: ArgumentResolver? = null
)

typealias StringProvider = StoryApi.() -> LocalizedStringProxy

fun StringProvider.translate(engine: StoryEngine): String =
    engine.story.api.this().let { proxy ->
        when (val loc = proxy.localizedString.handler) {
            is Simple -> proxy.simple(engine)
            is Selection -> proxy.selection(engine, loc)
            is Inflection -> proxy.inflection(engine, loc)
            is Composite -> proxy.composite(engine)
        }
    }

