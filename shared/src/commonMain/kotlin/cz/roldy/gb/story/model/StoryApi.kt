package cz.roldy.gb.story.model

import cz.roldy.gb.story.localization.Genus
import kotlinx.serialization.Serializable

@Serializable
data class StoryApi(
    val id: String,
    val supportedGenus: List<Genus>,
    val text: LocalizedString,
    val inflectionText: LocalizedString,
    val selectiveText: LocalizedString,
    val compositeText: LocalizedString
)



