package compose.screen

import androidx.compose.runtime.Composable
import compose.screen.story.StoryScreenDefaults
import compose.screen.story.StoryScreen
import core.remember.getValue
import core.remember.lazyRemember
import core.story.loadStories
import core.story.loadStory
import cz.roldy.gb.story.localization.Genus


@Composable
fun DevStoryScreen() {
    val story by lazyRemember {
        loadStory(loadStories().first { it.id == "dev_story" })
    }
    story?.let {
        StoryScreen(
            StoryScreenDefaults(
                it,
                it.definition.characters.first(),
                Genus.Masculine
            )
        )
    }
}