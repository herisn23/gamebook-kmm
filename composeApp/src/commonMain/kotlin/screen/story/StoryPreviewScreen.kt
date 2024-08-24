package screen.story

import HeaderSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import component.MainButton
import component.StoryTitle
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.StoryMetadata
import kotlinx.coroutines.delay
import screen.Screen
import story.desc
import story.loadStory
import view.LoaderView

data object StoryPreview : Screen<StoryMetadata>

@Composable
fun StoryPreviewScreen(
    metadata: StoryMetadata,
    onBack: () -> Unit,
    onStart: (Story) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Box(Modifier.height(HeaderSize)) {
                StoryTitle(metadata)
            }

            Text(metadata.desc)
            LoaderView(
                null,
                { this != null },
                { delay(10000); loadStory(metadata) },
                customLoadingView = {
                    Column {
                        Text(it)
                        MainButton(onBack) {
                            Text("Back")
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { story ->
                Row {
                    MainButton(onBack) {
                        Text("Back")
                    }
                    story?.let {
                        MainButton({
                            onStart(it)
                        }) {
                            Text("Pick")
                        }
                    }
                }
            }
        }
    }

}