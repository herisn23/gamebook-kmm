package compose.screen.story

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.api.StoryCharacter
import engine.StoryEngine
import engine.pickLanguage
import compose.router.Screen


data object StoryScreen : Screen<StoryInGameDefaults>

data class StoryInGameDefaults(
    val story: Story,
    val character: StoryCharacter,
    val genus: Genus
)

@Composable
fun StoryScreen(defaults: StoryInGameDefaults) {
    val (story, character, genus) = defaults
    val locale by remember { mutableStateOf(story.pickLanguage(Locale.current.language)) }
    Column {
        val engine =
            StoryEngine(story, genus) {
                locale
            }
//        Text(engine.t { text() })
//        val a = engine.t {
//            compositeText { selector ->
//                "ABC"
//            }
//        }
    }
}