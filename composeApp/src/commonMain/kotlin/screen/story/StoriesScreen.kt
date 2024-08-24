package screen.story

import DefaultPadding
import HeaderSize
import ScreenPadding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.node.Ref
import androidx.compose.ui.unit.IntSize
import component.StoryTitle
import cz.roldy.gb.story.model.StoryMetadata
import remember.lazyRemember
import screen.Screen
import story.loadStories

data object StoriesScreen : Screen<Any?> {
    fun target(): Screen.Target<Any?> =
        target(null)
}

@Composable
fun StoriesScreen(
    modifier: Modifier = Modifier,
    onStoryPick: (coord: LayoutCoordinates?, StoryMetadata) -> Unit
) {

    val stories by lazyRemember(emptyList()) { loadStories() }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(DefaultPadding),
        contentPadding = PaddingValues(DefaultPadding),
        modifier = modifier
            .fillMaxSize()
            .padding(ScreenPadding)
    ) {
        items(stories) { story ->
            val pos = remember { Ref<LayoutCoordinates>() }
            Card(
                onClick = {
                    onStoryPick(pos.value,  story)
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