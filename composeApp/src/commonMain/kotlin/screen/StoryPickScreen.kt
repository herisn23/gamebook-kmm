package screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import routing.RouteBuilder
import routing.RoutePathVars
import story.model.StoryMetadata

interface StoryPickVars : RoutePathVars {
    val id: String
}

val StoryPickRoute by lazy {
    RouteBuilder<StoryPickVars>()
        .addPathVariable("storyLoader")
        .addPathVariable("pick")
        .addPathVariable(StoryPickVars::id, NavType.StringType)
        .build()
}

@Composable
fun StoryPickScreen(
    storyMetadata: StoryMetadata,
    onStoryPick: (StoryMetadata) -> Unit,
    onBack: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = storyMetadata.name,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
            Text(storyMetadata.desc)
            Button(onBack) {
                Text("Back")
            }
            Button({
                onStoryPick(storyMetadata)
            }) {
                Text("Pick")
            }
        }
    }

}