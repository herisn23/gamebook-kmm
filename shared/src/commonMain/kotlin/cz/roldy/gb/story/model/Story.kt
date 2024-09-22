package cz.roldy.gb.story.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryMetadata(
    val id: String,
    val supportedLanguages: List<String>,
    override val i18n: I18NSource
) : MessageSource

@Serializable
data class Story(
    val definition: StoryDefinition,
    val metadata: StoryMetadata,
    override val i18n: I18NSource
) : MessageSource {
    val supportedLanguages by lazy { i18n.keys }
}