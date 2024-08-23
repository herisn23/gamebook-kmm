package cz.roldy.gb.story.localization.handler

import cz.roldy.gb.story.localization.IStoryLocalizedContext
import cz.roldy.gb.story.localization.Inflection
import cz.roldy.gb.story.localization.LocalizedStringProxy
import cz.roldy.gb.story.localization.invoke
import cz.roldy.gb.story.model.get

fun LocalizedStringProxy.inflection(
    engine: IStoryLocalizedContext,
    handler: Inflection
): String =
    when (handler.type) {
        is Inflection.Genus -> engine.story[engine.locale(), localizedString(engine.genus.code)]
    }