package engine.localization.handler

import engine.StoryEngine
import engine.localization.LocalizedStringProxy

fun LocalizedStringProxy.simple(engine: StoryEngine) =
    engine.story[localizedString.key]