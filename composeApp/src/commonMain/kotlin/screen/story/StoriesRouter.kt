package screen.story

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import screen.ScreenRouter
import screen.exit


@Composable
fun StoriesRouter() {
    ScreenRouter(StoriesScreen) {
        //List of stories
        add(StoriesScreen) { router ->
            StoriesScreen(fadeIn() exit fadeOut()) { coord, story ->
                val y = coord?.positionInRoot()?.y?.toInt() ?: 0
                router.navigate(
                    StoryPreparation.target(story),
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
            StoryPreparationView(
                router.data,
                onBack = {
                    router.navigate(
                        StoriesScreen.target(),
                        transition = fadeIn(tween(500)) exit fadeOut(),
                    )
                },
                onStart = { story ->
                    router.navigate(
                        StoryScreen.target(story),
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

