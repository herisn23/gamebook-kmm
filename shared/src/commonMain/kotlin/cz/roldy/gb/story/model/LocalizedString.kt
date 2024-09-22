package cz.roldy.gb.story.model

import cz.roldy.gb.story.localization.LocalizedStringHandler
import kotlinx.serialization.Serializable


@Serializable
data class LocalizedString(
    val key: String,
    val handler: LocalizedStringHandler
)