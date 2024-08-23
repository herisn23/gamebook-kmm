package cz.roldy.gb.story.localization.handler

import cz.roldy.gb.story.localization.BasicStringHandler
import cz.roldy.gb.story.localization.CompositeLocalizedString
import cz.roldy.gb.story.localization.IStoryLocalizedContext
import cz.roldy.gb.story.localization.Inflection
import cz.roldy.gb.story.localization.LocalizedStringProxy
import cz.roldy.gb.story.localization.Simple
import cz.roldy.gb.story.localization.handle
import cz.roldy.gb.story.model.LocalizedString
import cz.roldy.gb.story.model.get

fun LocalizedStringProxy.composite(
    context: IStoryLocalizedContext
): String =
    CompositeLocalizedString(context.story[context.locale(), localizedString.key]).run {
        arguments.map { (key, selector) ->
            key to when (selector.type) {
                //resolve value by Selector.Type
                cz.roldy.gb.story.localization.CompositeLocalizedString.Selector.Type.LocalizedInflectionText -> {
                    val type = Inflection.ofCode(selector.arguments.first())
                    val inflectionText = LocalizedString(
                        selector.key,
                        type
                    )
                    copy(localizedString = inflectionText).inflection(context, type)
                }

                cz.roldy.gb.story.localization.CompositeLocalizedString.Selector.Type.LocalizedText -> {
                    val text = LocalizedString(
                        selector.key,
                        Simple
                    )
                    copy(localizedString = text).simple(context)
                }

                cz.roldy.gb.story.localization.CompositeLocalizedString.Selector.Type.Argument -> argumentDelegate?.invoke(
                    selector
                )
            }.run {
                fun List<BasicStringHandler>.chain(string: String?): String? =
                    when (isEmpty()) {
                        true -> string
                        else -> first().handle(string).let { str->
                            subList(1, size).chain(str)
                        }
                    }

                selector.handlersChain.chain(this)
            }
        }.toMap().let(::resolve)
    }