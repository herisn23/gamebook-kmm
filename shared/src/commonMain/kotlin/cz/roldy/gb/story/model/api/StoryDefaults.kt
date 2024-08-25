package cz.roldy.gb.story.model.api

import cz.roldy.gb.story.localization.Genus
import kotlinx.serialization.Serializable

@Serializable
data class StoryDefaults(
    val id: String,
    val supportedGenus: List<Genus>
)
