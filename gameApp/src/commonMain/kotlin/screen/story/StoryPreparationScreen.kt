package screen.story

import DefaultPadding
import ScreenPadding
import StoryTileSize
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.painter.Painter
import cardDefaultColors
import compose.component.Loader
import core.story.desc
import core.story.loadStory
import core.story.startGameText
import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.model.StoryMetadata
import engine.pickLanguage
import gamebook.gamecore.generated.resources.error_retry
import router.Screen
import rt
import view.story.StoryCharacterView
import view.story.StoryTitleView

data object StoryPreparation : Screen<Pair<StoryMetadata, Painter>>

@Composable
fun StoryCharacterSelectionView(metadata: StoryMetadata, onStart: (StoryScreenDefaults) -> Unit) {
    Loader(
        null,
        { this != null },
        { loadStory(metadata) },
        errorButtonText = rt { error_retry },
        modifier = Modifier.fillMaxSize()
    ) { story ->
        story!!
        val locale by remember { mutableStateOf(story.pickLanguage()) }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(DefaultPadding),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = ScreenPadding)
        ) {
            items(story.definition.characters) {
                Column {
                    StoryCharacterView(
                        locale, story, it
                    ) {
                        onStart(StoryScreenDefaults(story, it, Genus.Masculine))
                    }
                }
            }
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
        Button(
            onNex,
            Modifier.align(Alignment.CenterHorizontally).padding(DefaultPadding)
        ) {
            Text(metadata.startGameText)
        }
    }
}


@Composable
fun StoryPreparationScreen(
    metadata: StoryMetadata,
    image: Painter,
    onBack: () -> Unit,
    onStart: (StoryScreenDefaults) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(ScreenPadding)) {
        Card(
            colors = cardDefaultColors
        ) {
            Box(Modifier.height(StoryTileSize)) {
                StoryTitleView(metadata, image) {
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

                        false -> StoryCharacterSelectionView(metadata, onStart)
                    }
                }
            }
        }
    }
}