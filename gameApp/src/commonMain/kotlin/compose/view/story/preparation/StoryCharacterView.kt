package compose.view.story.preparation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.api.StoryCharacter
import cz.roldy.gb.story.model.get


@Composable
fun StoryCharacterView(
    locale: String,
    story: Story,
    character: StoryCharacter,
    onPick:()->Unit
) {
    Card(
        onClick = onPick,
        modifier = Modifier
            .fillMaxWidth()
    ) {
       Text(story[locale, character.id])
    }
}