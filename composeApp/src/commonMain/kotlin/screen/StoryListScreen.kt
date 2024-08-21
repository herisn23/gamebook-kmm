package screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import routing.NoPathVars
import routing.RouteBuilder
import story.model.StoryMetadata


val StoriesRoute by lazy {
    RouteBuilder<NoPathVars>()
        .addPathVariable("stories")
        .build()
}

@Composable
fun StoriesScreen(
    stories: List<StoryMetadata>,
    onStoryPick: (StoryMetadata) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(100.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(stories) { story ->
            Card(
                onClick = {
                    onStoryPick(story)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = story.name,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}