package core.story

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import cz.roldy.gb.story.model.StoryMetadata
import cz.roldy.gb.story.model.get
import gamebook.client.lib.core.generated.resources.continue_
import rt


val StoryMetadata.name get() = this["name", Locale.current.language]
val StoryMetadata.desc get() = this["desc", Locale.current.language]

val StoryMetadata.startGameText
    @Composable
    get() = this["startGameText", Locale.current.language,  rt { continue_ }]