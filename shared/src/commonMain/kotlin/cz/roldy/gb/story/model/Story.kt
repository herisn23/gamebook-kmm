package cz.roldy.gb.story.model

import kotlinx.serialization.Serializable

typealias StoryLocalization = Map<String, Map<String, String>>

interface LocalizedStory {
    val localization: StoryLocalization
}

@Serializable
data class StoryMetadata(
    val id: String,
    override val localization: StoryLocalization
) : LocalizedStory

@Serializable
data class Story(
    val api: StoryApi,
    val metadata: StoryMetadata,
    override val localization: StoryLocalization
) : LocalizedStory {
    val supportedLanguages by lazy { localization.keys }
}