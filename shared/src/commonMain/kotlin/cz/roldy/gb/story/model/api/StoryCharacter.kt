package cz.roldy.gb.story.model.api

import kotlinx.serialization.Serializable

@Serializable
data class StoryCharacters(
    val characters: List<StoryCharacter>
)

@Serializable
data class StoryCharacter(
    val id: String,
    val startSection: Int
)
