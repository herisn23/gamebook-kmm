package cz.roldy.gb.story.model.api

import cz.roldy.gb.story.model.LocalizedString
import kotlinx.serialization.Serializable

@Serializable
data class StorySections(
    val sections: List<StorySection>
)

@Serializable
data class StorySection(
    val id: Int,
    val text: LocalizedString,
    val visitedText: LocalizedString? = null,
    val options: List<StoryOption>
)

@Serializable
data class StoryOption(
    val id: Int,
    val text: LocalizedString,
    val section: Int
)