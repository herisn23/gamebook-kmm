package cz.roldy.gb.story.model

import cz.roldy.gb.story.model.api.StoryCharacter
import cz.roldy.gb.story.model.api.StoryDefaults
import cz.roldy.gb.story.model.api.StorySection
import kotlinx.serialization.Serializable

@Serializable
data class StoryDefinition(
    val defaults: StoryDefaults,
    val sections: List<StorySection>,
    val characters: List<StoryCharacter>
) {
    fun findSection(id: Int) =
        sections.first { it.id == id }
}