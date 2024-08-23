package story

import androidx.compose.ui.text.intl.Locale
import cz.roldy.gb.story.model.StoryMetadata
import cz.roldy.gb.story.model.get


val StoryMetadata.name get() = this[Locale.current.language, "name"]
val StoryMetadata.desc get() = this[Locale.current.language, "desc"]