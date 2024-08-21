package engine.localization.handler

import engine.StoryEngine
import engine.localization.LocalizedStringProxy
import engine.localization.Selection
import engine.localization.invoke

fun LocalizedStringProxy.selection(
    engine: StoryEngine,
    handler: Selection
) = engine.story[localizedString(handler.selections.toString())]