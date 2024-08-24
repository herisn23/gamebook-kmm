package cz.roldy.gb.story.model

import cz.roldy.gb.story.localization.LocalizedStringHandler
import kotlinx.serialization.Serializable


@Serializable
data class LocalizedString(
    val key: String,
    val handler: LocalizedStringHandler
)

operator fun LocalizedStory.get(
    locale: String,
    key: String,
    defaultText: String = "__${locale}__${key}__"
) =
    localization[locale]?.get(key) ?: defaultText