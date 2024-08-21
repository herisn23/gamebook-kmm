package story.model

import androidx.compose.ui.text.intl.Locale
import kotlin.reflect.KProperty

typealias StoryLocalization = Map<String, Map<String, String>>

interface LocalizedStory {
    val localization: StoryLocalization

    operator fun get(key: String) =
        this[Locale.current, key]

    operator fun get(locale: Locale, key: String) =
        localization[key]?.get(locale.language) ?: "__${locale}__${key}__"

    operator fun getValue(nothing: Any?, property: KProperty<*>): String =
        this[property.name]
}

data class StoryMetadata(
    val id: String,
    override val localization: StoryLocalization
) : LocalizedStory {
    val name by this
    val desc by this
}

data class Story(
    val api: StoryApi,
    val metadata: StoryMetadata,
    override val localization: StoryLocalization
) : LocalizedStory