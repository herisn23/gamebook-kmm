import androidx.compose.runtime.Composable
import core.remember.getValue
import core.remember.lazyRemember
import core.story.loadStories
import core.story.loadStory
import cz.roldy.gb.story.localization.Genus
import screen.story.StoryScreen
import screen.story.StoryScreenDefaults


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