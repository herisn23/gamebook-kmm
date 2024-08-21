package engine.localization.handler

import engine.StoryEngine
import engine.localization.BasicStringHandler
import engine.localization.CompositeLocalizedString
import engine.localization.Inflection
import engine.localization.LocalizedStringProxy
import engine.localization.Simple
import engine.localization.handle
import story.model.LocalizedString

fun LocalizedStringProxy.composite(
    engine: StoryEngine
): String =
    CompositeLocalizedString(engine.story[localizedString.key]).run {
        arguments.map { (key, selector) ->
            key to when (selector.type) {
                //resolve value by Selector.Type
                CompositeLocalizedString.Selector.Type.LocalizedInflectionText -> {
                    val type = Inflection.ofCode(selector.arguments.first())
                    val inflectionText = LocalizedString(
                        selector.key,
                        type
                    )
                    copy(localizedString = inflectionText).inflection(engine, type)
                }

                CompositeLocalizedString.Selector.Type.LocalizedText -> {
                    val text = LocalizedString(
                        selector.key,
                        Simple
                    )
                    copy(localizedString = text).simple(engine)
                }

                CompositeLocalizedString.Selector.Type.Argument -> argumentDelegate?.invoke(selector)
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