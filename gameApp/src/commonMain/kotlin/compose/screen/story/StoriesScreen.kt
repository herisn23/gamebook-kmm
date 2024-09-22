package compose.screen.story

import DefaultPadding
import ScreenPadding
import StoryTileSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.node.Ref
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import compose.view.story.preparation.StoryTitleView
import cz.roldy.gb.story.model.StoryMetadata
import defaultBackgroundColor
import defaultColors
import gamebook.gameapp.generated.resources.Res
import gamebook.gameapp.generated.resources.error_retry
import gamebook.gameapp.generated.resources.stories
import gamebook.gameapp.generated.resources.story_default
import org.jetbrains.compose.resources.stringResource
import rp
import compose.router.Screen
import compose.router.ScreenTransition
import core.story.loadStories
import rt
import titleFont
import compose.component.Loader

data object StoriesScreen : Screen<Any?> {
    fun target(): Screen.Target<Any?> =
        target(null)
}

@Composable
fun StoriesScreen(
    transition: ScreenTransition,
    onStoryPick: (LayoutCoordinates?, StoryMetadata, Painter) -> Unit
) {

    Column {
        Box(Modifier.background(defaultBackgroundColor.copy(.2f))) {
            Text(
                stringResource(Res.string.stories),
                color = defaultColors.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(DefaultPadding),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontFamily = titleFont
            )
        }
        Loader(
            emptyList(),
            { isNotEmpty() },
            { loadStories() },
            errorButtonText = rt { error_retry },
            contentTransition = transition,
            errorTransition = transition,
            loadingTransition = transition
        )
        { stories ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(DefaultPadding),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = ScreenPadding)
            ) {
                items(stories) { story ->
                    val image = remember { Ref<Painter>() }
                    val defaultImage = rp { story_default }
                    val pos = remember { Ref<LayoutCoordinates>() }
                    Card(
                        onClick = {
                            onStoryPick(pos.value, story, image.value ?: defaultImage)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(StoryTileSize)
                            .onGloballyPositioned {
                                pos.value = it
                            }
                    ) {
                        StoryTitleView(story, onImageLoaded = { image.value = it })
                    }
                }
            }
        }
    }
}