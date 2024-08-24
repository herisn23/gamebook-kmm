package screen.story

import DefaultPadding
import HeaderSize
import ScreenPadding
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cardDefaultColors
import component.MainButton
import component.StoryTitle
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.StoryMetadata
import mrs
import screen.Screen
import story.desc
import story.loadStory
import story.startGameText
import view.LoaderView

data object StoryPreparation : Screen<StoryMetadata>

@Composable
fun StoryStartView(metadata: StoryMetadata, onStart: (Story) -> Unit) {
    LoaderView(
        null,
        { this != null },
        { loadStory(metadata) },
        errorButtonText = mrs { error_retry },
        modifier = Modifier.fillMaxSize()
    ) { story ->
        Text("available start positions in story")
        MainButton({
            onStart(story!!)
        }) {
            Text("Pick")
        }
    }
}

@Composable
fun StoryDescriptionView(metadata: StoryMetadata, onNex: () -> Unit) {
    Column {
        LazyColumn(Modifier.weight(1f).fillMaxSize().padding(DefaultPadding)) {
            item {
                Text(metadata.desc)
            }
        }
        MainButton(
            onNex,
            Modifier.align(Alignment.CenterHorizontally).padding(DefaultPadding)
        ) {
            Text(metadata.startGameText)
        }
    }
}


@Composable
fun StoryPreparationView(
    metadata: StoryMetadata,
    onBack: () -> Unit,
    onStart: (Story) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(ScreenPadding)) {
        Card(
            colors = cardDefaultColors
        ) {
            Box(Modifier.height(HeaderSize)) {
                StoryTitle(metadata) {
                    IconButton(onBack, Modifier.align(Alignment.TopEnd)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Back"
                        )
                    }
                }
            }
            Box {
                var description by remember { mutableStateOf(true) }
                AnimatedContent(
                    description,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    }
                ) { state ->
                    when (state) {
                        true -> StoryDescriptionView(metadata) {
                            description = false
                        }

                        false -> StoryStartView(metadata, onStart)
                    }
                }
            }
        }
    }
}