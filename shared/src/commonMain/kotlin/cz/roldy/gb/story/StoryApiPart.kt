package cz.roldy.gb.story

import cz.roldy.gb.story.model.api.StoryCharacters
import cz.roldy.gb.story.model.api.StoryDefaults
import cz.roldy.gb.story.model.api.StorySections
import kotlinx.serialization.KSerializer

interface StoryApiPart<T> {
    val id: String
    val serializer: KSerializer<T>

    fun path() = "api/$id.yaml"
}

data object SectionsApiPart : StoryApiPart<StorySections> {
    override val id: String = "sections"
    override val serializer get() = StorySections.serializer()

}

data object DefaultsApiPart : StoryApiPart<StoryDefaults> {
    override val id: String = "defaults"
    override val serializer get() = StoryDefaults.serializer()

}

data object CharactersApiPart : StoryApiPart<StoryCharacters> {
    override val id: String = "characters"
    override val serializer get() = StoryCharacters.serializer()

}