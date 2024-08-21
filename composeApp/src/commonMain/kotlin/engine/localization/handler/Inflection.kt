package engine.localization.handler

import engine.StoryEngine
import engine.localization.Inflection
import engine.localization.LocalizedStringProxy
import engine.localization.invoke

fun LocalizedStringProxy.inflection(
    engine: StoryEngine,
    handler: Inflection
): String =
    when (handler.type) {
        is Inflection.Genus -> engine.story[localizedString(engine.genus.code)]
    }