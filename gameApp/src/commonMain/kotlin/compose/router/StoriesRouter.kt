package compose.router

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import compose.screen.story.StoriesScreen
import compose.screen.story.StoryPreparation
import compose.screen.story.StoryPreparationScreen
import compose.screen.story.StoryScreen


@Composable
fun StoriesRouter() {
    ScreenRouter(StoriesScreen) {
        //List of stories
        add(StoriesScreen) { router ->
            StoriesScreen(fadeIn() exit fadeOut()) { coord, story, image ->
                val y = coord?.positionInRoot()?.y?.toInt() ?: 0
                router.navigate(
                    StoryPreparation.target(story to image),
                    transition = expandVertically(
                        expandFrom = BiasAlignment.Vertical(-1f)
                    ) {
                        coord?.size?.height ?: 0
                    } + slideIn {
                        IntOffset(0, y)
                    } exit fadeOut(),
                )
            }
        }

        //Story preview
        add(StoryPreparation) { router ->
            StoryPreparationScreen(
                router.data.first,
                router.data.second,
                onBack = {
                    router.navigate(
                        StoriesScreen.target(),
                        transition = fadeIn(tween(500)) exit fadeOut(),
                    )
                },
                onStart = { defaults ->
                    router.navigate(
                        StoryScreen.target(defaults),
                        transition = fadeIn() exit fadeOut(),
                    )
                })
        }

        //Begin Story
        add(StoryScreen) { router ->
            StoryScreen(router.data)
        }
    }
}

