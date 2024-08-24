package screen.story

import DefaultPadding
import HeaderSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.node.Ref
import component.StoryTitle
import cz.roldy.gb.story.model.StoryMetadata
import mrs
import screen.Screen
import screen.ScreenTransition
import story.loadStories
import view.LoaderView

data object StoriesScreen : Screen<Any?> {
    fun target(): Screen.Target<Any?> =
        target(null)
}

@Composable
fun StoriesScreen(
    transition: ScreenTransition,
    onStoryPick: (LayoutCoordinates?, StoryMetadata) -> Unit
) {
    LoaderView(
        emptyList(),
        { isNotEmpty() },
        { loadStories() },
        errorButtonText = mrs { error_retry },
        contentTransition = transition,
        errorTransition = transition,
        loadingTransition = transition
    )
    { stories ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(DefaultPadding),
            modifier = Modifier
                .fillMaxSize()
                .padding(DefaultPadding)
        ) {
            items(stories) { story ->
                val pos = remember { Ref<LayoutCoordinates>() }
                Card(
                    onClick = {
                        onStoryPick(pos.value, story)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HeaderSize)
                        .onGloballyPositioned {
                            pos.value = it
                        }
                ) {
                    StoryTitle(story)
                }
            }
        }
    }
}