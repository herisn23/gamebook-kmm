package cz.roldy.gb.story.localization.handler

import cz.roldy.gb.story.localization.IStoryLocalizedContext
import cz.roldy.gb.story.localization.LocalizedStringProxy
import cz.roldy.gb.story.model.get

fun LocalizedStringProxy.simple(context: IStoryLocalizedContext) =
    context.story[context.locale(), localizedString.key]