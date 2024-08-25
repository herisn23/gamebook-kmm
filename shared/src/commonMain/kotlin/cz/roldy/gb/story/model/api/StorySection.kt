package cz.roldy.gb.story.model.api

import kotlinx.serialization.Serializable

@Serializable
data class StorySections(
    val sections: List<StorySection>
)

@Serializable
data class StorySection(
    val id: String,
    val position: Int
)