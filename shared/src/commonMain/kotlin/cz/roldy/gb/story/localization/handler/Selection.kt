package cz.roldy.gb.story.localization.handler

import cz.roldy.gb.story.localization.IStoryLocalizedContext
import cz.roldy.gb.story.localization.LocalizedStringProxy
import cz.roldy.gb.story.localization.Selection
import cz.roldy.gb.story.localization.invoke
import cz.roldy.gb.story.model.get

fun LocalizedStringProxy.selection(
    context: IStoryLocalizedContext,
    handler: Selection
) = context.story[context.locale(), localizedString(handler.selections.toString())]