package screen.story

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import com.mikepenz.markdown.m3.Markdown
import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.localization.invoke
import cz.roldy.gb.story.model.Story
import engine.StoryEngine
import screen.Screen


data object StoryScreen : Screen<Story>

@Composable
fun StoryScreen(story: Story) {
    var selected by remember { mutableStateOf(true) }

    Column {
        val engine =
            StoryEngine(story, Genus.Masculine.takeIf { selected } ?: Genus.Feminine) {
                Locale.current.language
            }
        Text(engine.t { text() })
        val a = engine.t {
            compositeText { selector ->
                "ABC"
            }
        }

        Markdown(a)
        Button({
            selected = !selected
        }) {
            Text("Klik")
        }
        Button({

        }) {
            Text("Back")
        }
    }
}