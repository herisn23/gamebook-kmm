package screen.story

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.node.Ref
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import screen.ScreenRouter

@Composable
fun StoriesRouter() {
    ScreenRouter(StoriesScreen) {
        //List of stories
        add(StoriesScreen) { router ->
            StoriesScreen { coord, story ->
                val y = coord?.positionInRoot()?.y?.toInt() ?: 0
                router.navigate(
                    StoryPreview.target(story),
                    enter =
                    expandVertically(
                        expandFrom = BiasAlignment.Vertical(-1f)
                    ) {
                        coord?.size?.height ?: 0
                    } + slideIn {
                        IntOffset(0, y)
                    },
                    exit = fadeOut()
                )
            }
        }

        //Story preview
        add(StoryPreview) { router ->
            StoryPreviewScreen(router.data, {
                router.navigate(
                    StoriesScreen.target(),
                    enter = fadeIn(animationSpec = tween(1000)),
                    exit = fadeOut()
                )
            }) { story ->
                router.navigate(StoryScreen.target(story),
                    enter = fadeIn(animationSpec = tween(1000)),
                    exit = fadeOut()
                )
            }
        }

        //Begin Story
        add(StoryScreen) { router ->
            StoryScreen(router.data)
        }
    }
}

