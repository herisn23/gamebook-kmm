package story

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import cz.roldy.gb.story.model.StoryMetadata
import cz.roldy.gb.story.model.get
import t


val StoryMetadata.name get() = this["name", Locale.current.language]
val StoryMetadata.desc get() = this["desc", Locale.current.language]

val StoryMetadata.startGameText
    @Composable
    get() = this["startGameText", Locale.current.language, t { continue_ }]