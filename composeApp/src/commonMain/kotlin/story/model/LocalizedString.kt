package story.model

import engine.localization.LocalizedStringHandler
import kotlinx.serialization.Serializable

@Serializable
data class LocalizedString(
    val key: String,
    val handler: LocalizedStringHandler
)