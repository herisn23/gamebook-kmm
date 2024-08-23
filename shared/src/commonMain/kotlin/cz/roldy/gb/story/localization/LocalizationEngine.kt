package cz.roldy.gb.story.localization

import cz.roldy.gb.story.localization.handler.composite
import cz.roldy.gb.story.localization.handler.inflection
import cz.roldy.gb.story.localization.handler.selection
import cz.roldy.gb.story.localization.handler.simple
import cz.roldy.gb.story.model.LocalizedString
import cz.roldy.gb.story.model.StoryApi

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

fun <T : IStoryLocalizedContext> StringProvider.translate(engine: T): String =
    engine.story.api.this().let { proxy ->
        when (val loc = proxy.localizedString.handler) {
            is Simple -> proxy.simple(engine)
            is Selection -> proxy.selection(engine, loc)
            is Inflection -> proxy.inflection(engine, loc)
            is Composite -> proxy.composite(engine)
        }
    }

